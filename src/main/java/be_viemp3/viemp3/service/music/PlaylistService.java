package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.common.util.SecurityUtils;
import be_viemp3.viemp3.dto.request.music.playlist.AddSongToPlaylistRequest;
import be_viemp3.viemp3.dto.request.music.playlist.CreatePlaylistRequest;
import be_viemp3.viemp3.dto.request.music.playlist.RemoveSongToPlaylistRequest;
import be_viemp3.viemp3.dto.request.music.playlist.UpdatePlaylistRequest;
import be_viemp3.viemp3.dto.response.music.PlaylistResponse;
import be_viemp3.viemp3.entity.Playlist;
import be_viemp3.viemp3.entity.Song;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.mapper.music.PlaylistMapper;
import be_viemp3.viemp3.repository.music.PlaylistRepository;
import be_viemp3.viemp3.service.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final EntityQueryService entityQueryService;
    private final FileStorageService fileStorageService;
    private final SecurityUtils securityUtils;

    // ===== CREATE =====
    public PlaylistResponse createPlaylist(CreatePlaylistRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Tên playlist là bắt buộc");
        }
        User user = securityUtils.getCurrentUser();
        String coverUrl = null;
        if (request.getCover() != null && !request.getCover().isEmpty()) {
            coverUrl = fileStorageService.upload(request.getCover(), "playlists");
        }
        Playlist playlist = new Playlist();
        playlist.setName(request.getName().trim());
        playlist.setCover(coverUrl);
        playlist.setUser(user);
        playlist.setSongs(new ArrayList<>());
        playlistRepository.save(playlist);
        return PlaylistMapper.toResponse(playlist);
    }

    // ===== UPDATE =====
    public PlaylistResponse updatePlaylist(UpdatePlaylistRequest request) {
        Playlist playlist = entityQueryService.findPlaylistById(request.getPlaylistId());
        User user = securityUtils.getCurrentUser();
        // check owner
        if (!playlist.getUser().getEmail().equals(user.getEmail())) {
            throw new IllegalArgumentException("Bạn không có quyền chỉnh sửa playlist này");
        }
        boolean isUpdated = false;
        // update name
        if (request.getName() != null && !request.getName().isBlank()) {
            playlist.setName(request.getName().trim());
            isUpdated = true;
        }
        // update cover
        if (request.getCover() != null && !request.getCover().isEmpty()) {
            if (playlist.getCover() != null && !playlist.getCover().isBlank()) {
                fileStorageService.deleteByUrl(playlist.getCover());
            }
            String newCover = fileStorageService.upload(request.getCover(), "playlists");
            playlist.setCover(newCover);
            isUpdated = true;
        }
        if (!isUpdated) {
            throw new IllegalArgumentException("Không có dữ liệu để cập nhật");
        }
        playlistRepository.save(playlist);
        return PlaylistMapper.toResponse(playlist);
    }

    // ===== DELETE =====
    public void deletePlaylist(String playlistId) {
        Playlist playlist = entityQueryService.findPlaylistById(playlistId);
        User user = securityUtils.getCurrentUser();
        if (!playlist.getUser().getEmail().equals(user.getEmail())) {
            throw new IllegalArgumentException("Bạn không có quyền xoá playlist này");
        }
        if (playlist.getCover() != null) {
            fileStorageService.deleteByUrl(playlist.getCover());
        }
        playlistRepository.delete(playlist);
    }

    // ===== ADD SONG TO PLAYLIST =====
    public void addSongToPlaylist(AddSongToPlaylistRequest request) {
        Playlist playlist = entityQueryService.findPlaylistById(request.getPlaylistId());
        User currentUser = securityUtils.getCurrentUser();
        if (!playlist.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Bạn không có quyền chỉnh sửa playlist này");
        }
        Song song = entityQueryService.findSongById(request.getSongId());
        if (playlist.getSongs().contains(song)) {
            return;
        }
        playlist.getSongs().add(song);
    }

    // ===== REMOVE SONG FROM PLAYLIST =====
    public void removeSongFromPlaylist(RemoveSongToPlaylistRequest request) {
        Playlist playlist = entityQueryService.findPlaylistById(request.getPlaylistId());
        User currentUser = securityUtils.getCurrentUser();
        if (!playlist.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Bạn không có quyền chỉnh sửa playlist này");
        }
        Song song = entityQueryService.findSongById(request.getSongId());
        if (!playlist.getSongs().contains(song)) {
            throw new IllegalStateException("Bài hát không tồn tại trong playlist");
        }
        playlist.getSongs().remove(song);
    }

    // ===== GET BY ID =====
    public PlaylistResponse getPlaylistById(String playlistId) {
        return PlaylistMapper.toResponse(entityQueryService.findPlaylistById(playlistId));
    }

    // ===== GET ALL BY USER =====
    public List<PlaylistResponse> getPlaylistsByUser() {
        User user = securityUtils.getCurrentUser();
        return PlaylistMapper.toResponseList(
                playlistRepository.findByUser(user)
        );
    }
}