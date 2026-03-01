package be_viemp3.viemp3.controller.music;

import be_viemp3.viemp3.common.response.ApiResponse;
import be_viemp3.viemp3.dto.response.music.FavoriteAlbumResponse;
import be_viemp3.viemp3.service.music.FavoriteAlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.vie-mp3-url}/favorite-albums")
@RequiredArgsConstructor
public class FavoriteAlbumController {

    private final FavoriteAlbumService favoriteAlbumService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{albumId}")
    public ResponseEntity<ApiResponse<Void>> addAlbumToFavorite(@PathVariable String albumId) {
        favoriteAlbumService.addAlbumToFavorite(albumId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Đã thêm album vào danh sách yêu thích")
                        .build()
        );
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{albumId}")
    public ResponseEntity<ApiResponse<Void>> removeAlbumFromFavorite(@PathVariable String albumId) {
        favoriteAlbumService.removeAlbumFromFavorite(albumId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Đã xóa album khỏi danh sách yêu thích")
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<List<FavoriteAlbumResponse>>> getMyFavoriteAlbums() {
        List<FavoriteAlbumResponse> responses = favoriteAlbumService.getMyFavoriteAlbums();
        return ResponseEntity.ok(
                ApiResponse.<List<FavoriteAlbumResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách album yêu thích thành công")
                        .data(responses)
                        .build()
        );
    }
}
