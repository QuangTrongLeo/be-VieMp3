package be_viemp3.viemp3.controller.music;

import be_viemp3.viemp3.common.response.ApiResponse;
import be_viemp3.viemp3.dto.request.music.artist.CreateAristRequest;
import be_viemp3.viemp3.dto.request.music.artist.UpdateArtistRequest;
import be_viemp3.viemp3.dto.response.music.ArtistResponse;
import be_viemp3.viemp3.service.music.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.vie-mp3-url}/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    // ===== CREATE =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ArtistResponse>> createArtist(@ModelAttribute CreateAristRequest request) {
        ArtistResponse response = artistService.createArtist(request);
        return ResponseEntity.ok(
                ApiResponse.<ArtistResponse>builder()
                        .success(true)
                        .message("Tạo nghệ sĩ thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== UPDATE =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ArtistResponse>> updateArtist(@ModelAttribute UpdateArtistRequest request) {
        ArtistResponse response = artistService.updateArtist(request);
        return ResponseEntity.ok(
                ApiResponse.<ArtistResponse>builder()
                        .success(true)
                        .message("Cập nhật nghệ sĩ thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== DELETE =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteArtist(@RequestParam("artistId") UUID artistId) {
        artistService.deleteArtistById(artistId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Xóa nghệ sĩ thành công")
                        .build()
        );
    }

    // ===== GET BY NAME =====
    @PreAuthorize("permitAll()")
    @GetMapping("/{artistName}")
    public ResponseEntity<ApiResponse<ArtistResponse>> getArtistByName(@PathVariable String artistName) {
        ArtistResponse response = artistService.getArtistByName(artistName);
        return ResponseEntity.ok(
                ApiResponse.<ArtistResponse>builder()
                        .success(true)
                        .message("Lấy thông tin nghệ sĩ thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== GET ALL =====
    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ArtistResponse>>> getAllArtists() {
        List<ArtistResponse> responses = artistService.getAllArtists();
        return ResponseEntity.ok(
                ApiResponse.<List<ArtistResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách nghệ sĩ thành công")
                        .data(responses)
                        .build()
        );
    }
}