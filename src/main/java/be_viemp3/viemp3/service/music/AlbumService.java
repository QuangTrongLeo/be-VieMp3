package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.dto.request.music.album.AddSongToAlbumRequest;
import be_viemp3.viemp3.dto.request.music.album.CreateAlbumRequest;
import be_viemp3.viemp3.dto.request.music.album.UpdateAlbumRequest;
import be_viemp3.viemp3.dto.response.music.AlbumResponse;
import be_viemp3.viemp3.entity.Album;
import be_viemp3.viemp3.entity.Artist;
import be_viemp3.viemp3.entity.Song;
import be_viemp3.viemp3.mapper.music.AlbumMapper;
import be_viemp3.viemp3.repository.music.AlbumRepository;
import be_viemp3.viemp3.service.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final EntityQueryService entityQueryService;
    private final FileStorageService fileStorageService;

    // ===== CREATE =====
    public AlbumResponse createAlbum(CreateAlbumRequest request) {
        Artist artist = entityQueryService.findArtistById(request.getArtistId());
        String coverUrl = fileStorageService.upload(request.getCover(), "albums");
        Album album = new Album();
        album.setTitle(request.getTitle().trim());
        album.setCover(coverUrl);
        album.setArtist(artist);
        albumRepository.save(album);
        return AlbumMapper.toResponse(album);
    }

    // ===== UPDATE =====
    public AlbumResponse updateAlbum(UpdateAlbumRequest request) {
        Album album = entityQueryService.findAlbumById(request.getAlbumId());
        boolean isUpdated = false;
        // update title
        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            album.setTitle(request.getTitle().trim());
            isUpdated = true;
        }
        // update cover
        if (request.getCover() != null && !request.getCover().isEmpty()) {
            if (album.getCover() != null && !album.getCover().isBlank()) {
                fileStorageService.deleteByUrl(album.getCover());
            }
            String newCover = fileStorageService.upload(request.getCover(), "albums");
            album.setCover(newCover);
            isUpdated = true;
        }
        if (!isUpdated) {
            throw new IllegalArgumentException("Không có dữ liệu để cập nhật");
        }
        albumRepository.save(album);
        return AlbumMapper.toResponse(album);
    }

    // ===== DELETE =====
    public void deleteAlbum(String albumId) {
        Album album = entityQueryService.findAlbumById(albumId);
        if (album.getCover() != null) {
            fileStorageService.deleteByUrl(album.getCover());
        }
        albumRepository.delete(album);
    }

    // ===== ADD SONG TO ALBUM =====
    public void addSongToAlbum(AddSongToAlbumRequest request) {
        Song song = entityQueryService.findSongById(request.getSongId());
        Album album = entityQueryService.findAlbumById(request.getAlbumId());
        validateSameArtist(song, album);
        if (album.equals(song.getAlbum())) {
            return;
        }
        song.setAlbum(album);
    }

    // ===== REMOVE SONG FROM ALBUM =====
    public void removeSongFromAlbum(String songId) {
        Song song = entityQueryService.findSongById(songId);
        if (song.getAlbum() == null) {
            throw new IllegalStateException("Bài hát chưa thuộc album nào");
        }
        song.setAlbum(null);
    }

    // ===== GET BY ID =====
    public AlbumResponse getAlbumById(String albumId) {
        return AlbumMapper.toResponse(entityQueryService.findAlbumById(albumId));
    }

    // ===== GET ALL =====
    public List<AlbumResponse> getAllAlbums() {
        return AlbumMapper.toResponseList(albumRepository.findAll());
    }

    // ===== SUPPORT METHOD =====
    private void validateSameArtist(Song song, Album album) {
        if (!song.getArtist().getId().equals(album.getArtist().getId())) {
            throw new IllegalArgumentException("Song và Album không cùng nghệ sĩ");
        }
    }
}