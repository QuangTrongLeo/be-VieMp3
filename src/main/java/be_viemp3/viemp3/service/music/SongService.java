package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.dto.request.music.song.CreateSongRequest;
import be_viemp3.viemp3.dto.request.music.song.UpdateSongRequest;
import be_viemp3.viemp3.dto.response.music.SongResponse;
import be_viemp3.viemp3.entity.*;
import be_viemp3.viemp3.mapper.music.SongMapper;
import be_viemp3.viemp3.repository.music.SongRepository;
import be_viemp3.viemp3.service.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private final EntityQueryService entityQueryService;
    private final FileStorageService fileStorageService;

    // ===== CREATE =====
    public SongResponse createSong(CreateSongRequest request) {
        Artist artist = entityQueryService.findArtistById(request.getArtistId());
        Genre genre = entityQueryService.findGenreById(request.getGenreId());

        String coverUrl = fileStorageService.upload(request.getCover(), "songs/covers");
        String audioUrl = fileStorageService.upload(request.getAudio(), "songs/audios");

        Song song = new Song();
        song.setTitle(request.getTitle().trim());
        song.setDescription(request.getDescription());
        song.setCover(coverUrl);
        song.setAudio(audioUrl);
        song.setArtist(artist);
        song.setGenre(genre);
        song.setAlbum(null);
        songRepository.save(song);

        return SongMapper.toResponse(song);
    }

    // ===== UPDATE =====
    public SongResponse updateSong(UpdateSongRequest request) {
        Song song = entityQueryService.findSongById(request.getSongId());
        boolean isUpdated = false;

        // ===== Title =====
        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            song.setTitle(request.getTitle().trim());
            isUpdated = true;
        }
        // ===== Description =====
        if (request.getDescription() != null) {
            song.setDescription(request.getDescription());
            isUpdated = true;
        }
        // ===== Genre =====
        if (request.getGenreId() != null) {
            Genre genre = entityQueryService.findGenreById(request.getGenreId());
            song.setGenre(genre);
            isUpdated = true;
        }
        // ===== Album =====
        if (request.getAlbumId() != null) {
            Album album = entityQueryService.findAlbumById(request.getAlbumId());
            if (!album.getArtist().getId().equals(song.getArtist().getId())) {
                throw new IllegalArgumentException("Album không thuộc cùng nghệ sĩ");
            }
            song.setAlbum(album);
            isUpdated = true;
        }
        // ===== Cover =====
        if (request.getCover() != null && !request.getCover().isEmpty()) {
            if (song.getCover() != null) {
                fileStorageService.deleteByUrl(song.getCover());
            }
            String newCover = fileStorageService.upload(request.getCover(), "songs/covers");
            song.setCover(newCover);
            isUpdated = true;
        }
        // ===== Audio =====
        if (request.getAudio() != null && !request.getAudio().isEmpty()) {
            if (song.getAudio() != null) {
                fileStorageService.deleteByUrl(song.getAudio());
            }
            String newAudio = fileStorageService.upload(request.getAudio(), "songs/audios");
            song.setAudio(newAudio);
            isUpdated = true;
        }
        if (!isUpdated) {
            throw new IllegalArgumentException("Không có dữ liệu để cập nhật");
        }

        songRepository.save(song);
        return SongMapper.toResponse(song);
    }

    // ===== DELETE =====
    public void deleteSong(String songId) {
        Song song = entityQueryService.findSongById(songId);
        if (song.getCover() != null) {
            fileStorageService.deleteByUrl(song.getCover());
        }
        if (song.getAudio() != null) {
            fileStorageService.deleteByUrl(song.getAudio());
        }
        songRepository.delete(song);
    }

    // ===== GET BY ID =====
    public SongResponse getSongById(String songId) {
        return SongMapper.toResponse(entityQueryService.findSongById(songId));
    }

    // ===== GET ALL =====
    public List<SongResponse> getAllSongs() {
        return SongMapper.toResponseList(songRepository.findAll());
    }

    // ===== GET SONGS BY ARTIST =====
    public List<SongResponse> getSongsByArtist(String artistId) {
        entityQueryService.findArtistById(artistId);
        List<Song> songs = songRepository.findByArtistId(artistId);
        return SongMapper.toResponseList(songs);
    }

    // ===== GET SONGS BY ALBUM =====
    public List<SongResponse> getSongsByAlbum(String albumId) {
        entityQueryService.findAlbumById(albumId);
        List<Song> songs = songRepository.findByAlbumId(albumId);
        return SongMapper.toResponseList(songs);
    }

    // ===== GET SONGS BY PLAYLIST =====
    public List<SongResponse> getSongsByPlaylist(String playlistId) {
        Playlist playlist = entityQueryService.findPlaylistById(playlistId);
        List<Song> songs = playlist.getSongs();
        return SongMapper.toResponseList(songs);
    }
}
