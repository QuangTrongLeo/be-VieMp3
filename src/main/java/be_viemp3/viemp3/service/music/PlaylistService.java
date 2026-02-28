package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.dto.request.music.playlist.CreatePlaylistRequest;
import be_viemp3.viemp3.dto.request.music.playlist.UpdatePlaylistRequest;
import be_viemp3.viemp3.dto.response.music.PlaylistResponse;
import be_viemp3.viemp3.entity.Playlist;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.mapper.music.PlaylistMapper;
import be_viemp3.viemp3.repository.music.PlaylistRepository;
import be_viemp3.viemp3.service.auth.UserService;
import be_viemp3.viemp3.service.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    // ===== CREATE =====
    public PlaylistResponse createPlaylist(String userEmail, CreatePlaylistRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Tên playlist là bắt buộc");
        }

        User user = userService.findUserByEmail(userEmail);

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
    public PlaylistResponse updatePlaylist(UpdatePlaylistRequest request, String userEmail) {

        Playlist playlist = findPlaylistById(request.getPlaylistId());

        // check owner
        if (!playlist.getUser().getEmail().equals(userEmail)) {
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
    public void deletePlaylist(String playlistId, String userEmail) {

        Playlist playlist = findPlaylistById(playlistId);

        if (!playlist.getUser().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("Bạn không có quyền xoá playlist này");
        }

        if (playlist.getCover() != null) {
            fileStorageService.deleteByUrl(playlist.getCover());
        }

        playlistRepository.delete(playlist);
    }

    // ===== GET BY ID =====
    public PlaylistResponse getPlaylistById(String playlistId) {
        return PlaylistMapper.toResponse(findPlaylistById(playlistId));
    }

    // ===== GET ALL BY USER =====
    public List<PlaylistResponse> getPlaylistsByUser(String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        return PlaylistMapper.toResponseList(
                playlistRepository.findByUser(user)
        );
    }

    // ===== SUPPORT METHOD =====
    public Playlist findPlaylistById(String id) {
        return playlistRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Playlist không tồn tại"));
    }
}