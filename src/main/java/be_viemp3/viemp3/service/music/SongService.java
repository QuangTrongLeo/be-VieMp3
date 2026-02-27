package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.dto.request.music.song.CreateSongRequest;
import be_viemp3.viemp3.dto.request.music.song.UpdateSongRequest;
import be_viemp3.viemp3.dto.response.music.SongResponse;
import be_viemp3.viemp3.entity.Album;
import be_viemp3.viemp3.entity.Artist;
import be_viemp3.viemp3.entity.Genre;
import be_viemp3.viemp3.entity.Song;
import be_viemp3.viemp3.mapper.music.SongMapper;
import be_viemp3.viemp3.repository.music.SongRepository;
import be_viemp3.viemp3.service.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final GenreService genreService;
    private final FileStorageService fileStorageService;

    // ===== CREATE =====
    public SongResponse createSong(CreateSongRequest request) {
        Artist artist = artistService.findArtistById(request.getArtistId());
        Album album = albumService.findAlbumById(request.getAlbumId());
        Genre genre = genreService.findGenreById(request.getGenreId());

        String coverUrl = fileStorageService.upload(request.getCover(), "songs/covers");
        String audioUrl = fileStorageService.upload(request.getAudio(), "songs/audios");

        Song song = new Song();
        song.setTitle(request.getTitle().trim());
        song.setDescription(request.getDescription());
        song.setCover(coverUrl);
        song.setAudio(audioUrl);
        song.setArtist(artist);
        song.setAlbum(album);
        song.setGenre(genre);

        songRepository.save(song);

        return SongMapper.toResponse(song);
    }

    // ===== UPDATE =====
    public SongResponse updateSong(UpdateSongRequest request) {
        Song song = findSongById(request.getSongId());
        song.setTitle(request.getTitle().trim());
        song.setDescription(request.getDescription());
        songRepository.save(song);
        return SongMapper.toResponse(song);
    }

    // ===== UPDATE COVER =====
    public SongResponse updateSongCover(UUID songId, MultipartFile newCover) {
        Song song = findSongById(songId);
        if (newCover == null || newCover.isEmpty()) {
            throw new IllegalArgumentException("Cover file is required");
        }
        if (song.getCover() != null && !song.getCover().isBlank()) {
            fileStorageService.deleteByUrl(song.getCover());
        }
        String newCoverUrl = fileStorageService.upload(newCover, "songs/covers");
        song.setCover(newCoverUrl);
        songRepository.save(song);
        return SongMapper.toResponse(song);
    }

    // ===== UPDATE AUDIO =====
    public SongResponse updateSongAudio(UUID songId, MultipartFile newAudio) {
        Song song = findSongById(songId);
        if (newAudio == null || newAudio.isEmpty()) {
            throw new IllegalArgumentException("Audio file is required");
        }
        if (song.getAudio() != null && !song.getAudio().isBlank()) {
            fileStorageService.deleteByUrl(song.getAudio());
        }
        String newAudioUrl = fileStorageService.upload(newAudio, "songs/audios");
        song.setAudio(newAudioUrl);
        songRepository.save(song);
        return SongMapper.toResponse(song);
    }

    // ===== DELETE =====
    public void deleteSong(UUID songId) {
        Song song = findSongById(songId);
        if (song.getCover() != null) {
            fileStorageService.deleteByUrl(song.getCover());
        }
        if (song.getAudio() != null) {
            fileStorageService.deleteByUrl(song.getAudio());
        }
        songRepository.delete(song);
    }

    // ===== GET BY ID =====
    public SongResponse getSongById(UUID songId) {
        return SongMapper.toResponse(findSongById(songId));
    }

    // ===== GET ALL =====
    public List<SongResponse> getAllSongs() {
        return SongMapper.toResponseList(songRepository.findAll());
    }

    // ===== SUPPORT METHOD =====
    public Song findSongById(UUID id) {
        return songRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Bài hát không tồn tại với id: " + id));
    }
}
