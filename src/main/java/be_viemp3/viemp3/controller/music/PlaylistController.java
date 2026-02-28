package be_viemp3.viemp3.controller.music;

import be_viemp3.viemp3.common.response.ApiResponse;
import be_viemp3.viemp3.dto.request.music.playlist.CreatePlaylistRequest;
import be_viemp3.viemp3.dto.request.music.playlist.UpdatePlaylistRequest;
import be_viemp3.viemp3.dto.response.music.PlaylistResponse;
import be_viemp3.viemp3.service.music.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<ApiResponse<PlaylistResponse>> createPlaylist(
            @AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute CreatePlaylistRequest request
    ) {
        PlaylistResponse response = playlistService.createPlaylist(userDetails.getUsername(), request);
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
    public ResponseEntity<ApiResponse<PlaylistResponse>> updatePlaylist(
            @AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute UpdatePlaylistRequest request
    ) {
        PlaylistResponse response = playlistService.updatePlaylist(request, userDetails.getUsername());
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
    public ResponseEntity<ApiResponse<Void>> deletePlaylist(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String playlistId
    ) {
        playlistService.deletePlaylist(playlistId, userDetails.getUsername());
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Xoá playlist thành công")
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
    public ResponseEntity<ApiResponse<List<PlaylistResponse>>> getMyPlaylists(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<PlaylistResponse> response = playlistService.getPlaylistsByUser(userDetails.getUsername());
        return ResponseEntity.ok(
                ApiResponse.<List<PlaylistResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách playlist thành công")
                        .data(response)
                        .build()
        );
    }
}
