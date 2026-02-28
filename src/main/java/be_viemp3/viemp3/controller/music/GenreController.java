package be_viemp3.viemp3.controller.music;

import be_viemp3.viemp3.common.response.ApiResponse;
import be_viemp3.viemp3.dto.request.music.genre.CreateGenreRequest;
import be_viemp3.viemp3.dto.request.music.genre.UpdateGenreRequest;
import be_viemp3.viemp3.dto.response.music.GenreResponse;
import be_viemp3.viemp3.service.music.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.vie-mp3-url}/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    // ===== CREATE =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @PostMapping
    public ResponseEntity<ApiResponse<GenreResponse>> createGenre(@RequestBody @Valid CreateGenreRequest request) {
        GenreResponse response = genreService.createGenre(request);
        return ResponseEntity.ok(
                ApiResponse.<GenreResponse>builder()
                        .success(true)
                        .message("Tạo genre thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== GET ALL =====
    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<ApiResponse<List<GenreResponse>>> getAllGenres() {
        List<GenreResponse> response = genreService.genres();
        return ResponseEntity.ok(
                ApiResponse.<List<GenreResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách genre thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== GET BY ID =====
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GenreResponse>> getGenreById(@PathVariable UUID id) {
        GenreResponse response = genreService.getGenreById(id);
        return ResponseEntity.ok(
                ApiResponse.<GenreResponse>builder()
                        .success(true)
                        .message("Lấy genre thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== UPDATE =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @PutMapping
    public ResponseEntity<ApiResponse<GenreResponse>> updateGenre(@RequestBody @Valid UpdateGenreRequest request) {
        GenreResponse response = genreService.updateGenre(request);
        return ResponseEntity.ok(
                ApiResponse.<GenreResponse>builder()
                        .success(true)
                        .message("Cập nhật genre thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== DELETE =====
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteGenre(@PathVariable UUID id) {
        genreService.deleteGenre(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Xóa genre thành công")
                        .build()
        );
    }
}