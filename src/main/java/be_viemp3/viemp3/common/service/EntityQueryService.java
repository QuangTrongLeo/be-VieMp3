package be_viemp3.viemp3.common.service;

import be_viemp3.viemp3.entity.*;
import be_viemp3.viemp3.repository.music.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityQueryService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    // ===== ALBUM =====
    public Album findAlbumById(String id) {
        return albumRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Album không tồn tại"));
    }

    // ===== ARTIST =====
    public Artist findArtistById(String id) {
        return artistRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Nghệ sĩ không tồn tại với id: " + id));
    }

    // ===== GENRE =====
    public Genre findGenreById(String id) {
        return genreRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Genre không tồn tại với id: " + id));
    }

    // ===== PLAYLIST =====
    public Playlist findPlaylistById(String id) {
        return playlistRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Playlist không tồn tại"));
    }


    // ===== SONG =====
    public Song findSongById(String id) {
        return songRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Bài hát không tồn tại với id: " + id));
    }
}
