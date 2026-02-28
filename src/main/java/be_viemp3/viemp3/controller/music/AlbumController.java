package be_viemp3.viemp3.controller.music;

import be_viemp3.viemp3.common.response.ApiResponse;
import be_viemp3.viemp3.dto.request.music.album.CreateAlbumRequest;
import be_viemp3.viemp3.dto.request.music.album.UpdateAlbumRequest;
import be_viemp3.viemp3.dto.response.music.AlbumResponse;
import be_viemp3.viemp3.service.music.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.vie-mp3-url}/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    // ===== CREATE =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<AlbumResponse>> createAlbum(@ModelAttribute CreateAlbumRequest request) {
        AlbumResponse response = albumService.createAlbum(request);
        return ResponseEntity.ok(
                ApiResponse.<AlbumResponse>builder()
                        .success(true)
                        .message("Tạo album thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== UPDATE =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<AlbumResponse>> updateAlbum(@ModelAttribute UpdateAlbumRequest request) {
        AlbumResponse response = albumService.updateAlbum(request);
        return ResponseEntity.ok(
                ApiResponse.<AlbumResponse>builder()
                        .success(true)
                        .message("Cập nhật album thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== DELETE =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteAlbum(@RequestParam String albumId) {
        albumService.deleteAlbum(albumId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Xóa album thành công")
                        .build()
        );
    }

    // ===== GET BY ID =====
    @PreAuthorize("permitAll()")
    @GetMapping("/{albumId}")
    public ResponseEntity<ApiResponse<AlbumResponse>> getAlbum(@PathVariable String albumId) {
        AlbumResponse response = albumService.getAlbumById(albumId);
        return ResponseEntity.ok(
                ApiResponse.<AlbumResponse>builder()
                        .success(true)
                        .message("Lấy thông tin album thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== GET ALL =====
    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AlbumResponse>>> getAllAlbums() {
        List<AlbumResponse> responses = albumService.getAllAlbums();
        return ResponseEntity.ok(
                ApiResponse.<List<AlbumResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách album thành công")
                        .data(responses)
                        .build()
        );
    }
}