package be_viemp3.viemp3.controller.music;

import be_viemp3.viemp3.common.response.ApiResponse;
import be_viemp3.viemp3.dto.request.music.playlist.AddSongToPlaylistRequest;
import be_viemp3.viemp3.dto.request.music.playlist.CreatePlaylistRequest;
import be_viemp3.viemp3.dto.request.music.playlist.RemoveSongToPlaylistRequest;
import be_viemp3.viemp3.dto.request.music.playlist.UpdatePlaylistRequest;
import be_viemp3.viemp3.dto.response.music.PlaylistResponse;
import be_viemp3.viemp3.service.music.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.vie-mp3-url}/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    // ===== CREATE =====
    @PreAuthorize("hasRole('USER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PlaylistResponse>> createPlaylist(@ModelAttribute CreatePlaylistRequest request) {
        PlaylistResponse response = playlistService.createPlaylist(request);
        return ResponseEntity.ok(
                ApiResponse.<PlaylistResponse>builder()
                        .success(true)
                        .message("Tạo playlist thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== UPDATE =====
    @PreAuthorize("hasRole('USER')")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<PlaylistResponse>> updatePlaylist(@ModelAttribute UpdatePlaylistRequest request) {
        PlaylistResponse response = playlistService.updatePlaylist(request);
        return ResponseEntity.ok(
                ApiResponse.<PlaylistResponse>builder()
                        .success(true)
                        .message("Cập nhật playlist thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== DELETE =====
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{playlistId}")
    public ResponseEntity<ApiResponse<Void>> deletePlaylist(@PathVariable String playlistId) {
        playlistService.deletePlaylist(playlistId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Xoá playlist thành công")
                        .build()
        );
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/add-song")
    public ResponseEntity<ApiResponse<Void>> addSongToPlaylist(@RequestBody AddSongToPlaylistRequest request) {
        playlistService.addSongToPlaylist(request);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Thêm bài hát vào playlist thành công")
                        .build()
        );
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/remove-song")
    public ResponseEntity<ApiResponse<Void>> removeSongFromPlaylist(@RequestBody RemoveSongToPlaylistRequest request) {
        playlistService.removeSongFromPlaylist(request);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Đã xóa bài hát khỏi playlist")
                        .build()
        );
    }

    // ===== GET BY ID =====
    @GetMapping("/{playlistId}")
    public ResponseEntity<ApiResponse<PlaylistResponse>> getPlaylistById(@PathVariable String playlistId) {
        PlaylistResponse response = playlistService.getPlaylistById(playlistId);
        return ResponseEntity.ok(
                ApiResponse.<PlaylistResponse>builder()
                        .success(true)
                        .message("Lấy playlist thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== GET ALL PLAYLIST OF CURRENT USER =====
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<PlaylistResponse>>> getMyPlaylists() {
        List<PlaylistResponse> response = playlistService.getPlaylistsByUser();
        return ResponseEntity.ok(
                ApiResponse.<List<PlaylistResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách playlist thành công")
                        .data(response)
                        .build()
        );
    }
}
