package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.dto.request.music.song.AddSongToAlbumRequest;
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
        Genre genre = genreService.findGenreById(request.getGenreId());

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

    // ===== ADD SONG TO ALBUM =====
    public void addSongToAlbum(AddSongToAlbumRequest request) {
        Song song = findSongById(request.getSongId());
        Album album = albumService.findAlbumById(request.getAlbumId());
        validateSameArtist(song, album);
        if (album.equals(song.getAlbum())) {
            return;
        }
        song.setAlbum(album);
    }

    // ===== UPDATE =====
    public SongResponse updateSong(UpdateSongRequest request) {
        Song song = findSongById(request.getSongId());
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
            Genre genre = genreService.findGenreById(request.getGenreId());
            song.setGenre(genre);
            isUpdated = true;
        }
        // ===== Album =====
        if (request.getAlbumId() != null) {
            Album album = albumService.findAlbumById(request.getAlbumId());
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

    // ===== REMOVE SONG FROM ALBUM =====
    public void removeSongFromAlbum(UUID songId) {
        Song song = findSongById(songId);
        if (song.getAlbum() == null) {
            throw new IllegalStateException("Bài hát chưa thuộc album nào");
        }
        song.setAlbum(null);
    }

    // ===== GET BY ID =====
    public SongResponse getSongById(UUID songId) {
        return SongMapper.toResponse(findSongById(songId));
    }

    // ===== GET ALL =====
    public List<SongResponse> getAllSongs() {
        return SongMapper.toResponseList(songRepository.findAll());
    }

    // ===== GET ALL BY ARTIST =====
    public List<SongResponse> getSongsByArtist(UUID artistId) {
        artistService.findArtistById(artistId);
        List<Song> songs = songRepository.findByArtistId(artistId);
        return SongMapper.toResponseList(songs);
    }

    // ===== GET ALL BY ALBUM =====
    public List<SongResponse> getSongsByAlbum(UUID albumId) {
        albumService.findAlbumById(albumId);
        List<Song> songs = songRepository.findByAlbumId(albumId);
        return SongMapper.toResponseList(songs);
    }

    // ===== SUPPORT METHOD =====
    public Song findSongById(UUID id) {
        return songRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Bài hát không tồn tại với id: " + id));
    }

    private void validateSameArtist(Song song, Album album) {
        if (!song.getArtist().getId().equals(album.getArtist().getId())) {
            throw new IllegalArgumentException("Song và Album không cùng nghệ sĩ");
        }
    }
}
