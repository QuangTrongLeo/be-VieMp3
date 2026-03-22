package be_viemp3.viemp3.service.recomment;

import be_viemp3.viemp3.entity.ListenHistory;
import be_viemp3.viemp3.entity.Song;
import be_viemp3.viemp3.repository.music.ListenHistoryRepository;
import be_viemp3.viemp3.repository.music.SongRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final ListenHistoryRepository listenHistoryRepository;
    private final SongRepository songRepository;

    // ===== MAIN FUNCTION =====
    public List<Song> recommend(String userId) {
        List<ListenHistory> histories = listenHistoryRepository.findByUserId(userId);
        List<Song> allSongs = songRepository.findAll();

        if (histories.isEmpty()) {
            return allSongs.stream().limit(10).toList();
        }

        // ===== songs đã nghe =====
        Set<String> listenedIds = histories.stream()
                .map(h -> h.getSong().getId())
                .collect(Collectors.toSet());

        // ===== build index =====
        Map<String, Integer> genreIndex = buildGenreIndex(allSongs);
        Map<String, Integer> artistIndex = buildArtistIndex(allSongs);
        Map<String, Integer> albumIndex = buildAlbumIndex(allSongs);

        // ===== build user vector =====
        double[] userVector = buildUserVector(histories, genreIndex, artistIndex, albumIndex);

        // ===== scoring =====
        return allSongs.stream()
                .filter(song -> !listenedIds.contains(song.getId()))
                .sorted((a, b) -> Double.compare(
                        scoreSong(b, userVector, genreIndex, artistIndex, albumIndex),
                        scoreSong(a, userVector, genreIndex, artistIndex, albumIndex)
                ))
                .limit(10)
                .toList();
    }

    // ===== BUILD USER VECTOR =====
    private double[] buildUserVector(
            List<ListenHistory> histories,
            Map<String, Integer> genreIndex,
            Map<String, Integer> artistIndex,
            Map<String, Integer> albumIndex
    ) {
        int size = genreIndex.size() + artistIndex.size() + albumIndex.size();
        double[] userVector = new double[size];

        OffsetDateTime now = OffsetDateTime.now();

        for (ListenHistory lh : histories) {
            double[] songVector = vectorizeSong(lh.getSong(), genreIndex, artistIndex, albumIndex);

            // ===== TIME DECAY =====
            long days = ChronoUnit.DAYS.between(lh.getListenedAt(), now);
            double weight = Math.exp(-days / 7.0);

            for (int i = 0; i < size; i++) {
                userVector[i] += songVector[i] * weight;
            }
        }

        return normalize(userVector);
    }

    // ===== SCORE SONG =====
    private double scoreSong(
            Song song,
            double[] userVector,
            Map<String, Integer> genreIndex,
            Map<String, Integer> artistIndex,
            Map<String, Integer> albumIndex
    ) {
        double[] songVector = vectorizeSong(song, genreIndex, artistIndex, albumIndex);
        return cosineSimilarity(userVector, songVector);
    }

    // ===== COSINE USING LIBRARY =====
    private double cosineSimilarity(double[] a, double[] b) {
        RealVector v1 = new ArrayRealVector(a);
        RealVector v2 = new ArrayRealVector(b);

        double dotProduct = v1.dotProduct(v2);
        double normA = v1.getNorm();
        double normB = v2.getNorm();

        if (normA == 0 || normB == 0) return 0;

        return dotProduct / (normA * normB);
    }

    // ===== VECTORIZE SONG =====
    private double[] vectorizeSong(
            Song song,
            Map<String, Integer> genreIndex,
            Map<String, Integer> artistIndex,
            Map<String, Integer> albumIndex
    ) {
        int size = genreIndex.size() + artistIndex.size() + albumIndex.size();
        double[] vector = new double[size];

        // Genre weight = 0.5
        if (song.getGenre() != null) {
            int pos = genreIndex.get(song.getGenre().getId());
            vector[pos] = 0.5;
        }

        // Artist weight = 0.3
        if (song.getArtist() != null) {
            int pos = artistIndex.get(song.getArtist().getId()) + genreIndex.size();
            vector[pos] = 0.3;
        }

        // Album weight = 0.2
        if (song.getAlbum() != null) {
            int pos = albumIndex.get(song.getAlbum().getId())
                    + genreIndex.size()
                    + artistIndex.size();
            vector[pos] = 0.2;
        }

        return vector;
    }

    // ===== NORMALIZE =====
    private double[] normalize(double[] vector) {
        double sum = 0;
        for (double v : vector) sum += v * v;

        double norm = Math.sqrt(sum);
        if (norm == 0) return vector;

        for (int i = 0; i < vector.length; i++) {
            vector[i] /= norm;
        }

        return vector;
    }

    // ===== BUILD INDEX =====
    private Map<String, Integer> buildGenreIndex(List<Song> songs) {
        Map<String, Integer> map = new HashMap<>();
        int index = 0;

        for (Song s : songs) {
            if (s.getGenre() != null && !map.containsKey(s.getGenre().getId())) {
                map.put(s.getGenre().getId(), index++);
            }
        }
        return map;
    }

    private Map<String, Integer> buildArtistIndex(List<Song> songs) {
        Map<String, Integer> map = new HashMap<>();
        int index = 0;

        for (Song s : songs) {
            if (s.getArtist() != null && !map.containsKey(s.getArtist().getId())) {
                map.put(s.getArtist().getId(), index++);
            }
        }
        return map;
    }

    private Map<String, Integer> buildAlbumIndex(List<Song> songs) {
        Map<String, Integer> map = new HashMap<>();
        int index = 0;

        for (Song s : songs) {
            if (s.getAlbum() != null && !map.containsKey(s.getAlbum().getId())) {
                map.put(s.getAlbum().getId(), index++);
            }
        }
        return map;
    }
}