package be_viemp3.viemp3.controller.music;

import be_viemp3.viemp3.common.response.ApiResponse;
import be_viemp3.viemp3.dto.request.music.song.SongRequest;
import be_viemp3.viemp3.dto.response.music.SongResponse;
import be_viemp3.viemp3.service.music.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.vie-mp3-url}/songs")
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

    // ===== CREATE =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<SongResponse>> createSong(@ModelAttribute SongRequest request) {
        SongResponse response = songService.createSong(request);
        return ResponseEntity.ok(
                ApiResponse.<SongResponse>builder()
                        .success(true)
                        .message("Tạo bài hát thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== UPDATE =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<SongResponse>> updateSong(
            @PathVariable String id,
            @ModelAttribute SongRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<SongResponse>builder()
                        .success(true)
                        .message("Cập nhật bài hát thành công")
                        .data(songService.updateSong(id, request))
                        .build()
        );
    }

    // ===== DELETE =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteSong(@RequestParam String songId) {
        songService.deleteSong(songId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Xóa bài hát thành công")
                        .build()
        );
    }

    // ===== GET BY ID =====
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/{songId}")
    public ResponseEntity<ApiResponse<SongResponse>> getSongById(@PathVariable String songId) {
        SongResponse response = songService.getSongById(songId);
        return ResponseEntity.ok(
                ApiResponse.<SongResponse>builder()
                        .success(true)
                        .message("Lấy thông tin bài hát thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== GET ALL =====
    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SongResponse>>> getAllSongs() {
        List<SongResponse> responses = songService.getAllSongs();
        return ResponseEntity.ok(
                ApiResponse.<List<SongResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách bài hát thành công")
                        .data(responses)
                        .build()
        );
    }

    // ===== GET SONGS RECOMMEND =====
    @GetMapping("/recommend")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<List<SongResponse>>> recommendSongs() {
        List<SongResponse> songs = songService.recommendSongs();
        return ResponseEntity.ok(
                ApiResponse.<List<SongResponse>>builder()
                        .success(true)
                        .message("Gợi ý bài hát cá nhân hóa thành công")
                        .data(songs)
                        .build()
        );
    }

    // ===== GET ALL SONG BY ALBUM =====
    @PreAuthorize("permitAll()")
    @GetMapping("/album/{albumId}")
    public ResponseEntity<ApiResponse<List<SongResponse>>> getSongsByAlbum(@PathVariable String albumId) {
        List<SongResponse> songs = songService.getSongsByAlbum(albumId);
        return ResponseEntity.ok(
                ApiResponse.<List<SongResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách bài hát theo album thành công")
                        .data(songs)
                        .build()
        );
    }

    // ===== GET SONGS BY ARTIST =====
    @PreAuthorize("permitAll()")
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<ApiResponse<List<SongResponse>>> getSongsByArtist(@PathVariable String artistId) {
        List<SongResponse> response = songService.getSongsByArtist(artistId);
        return ResponseEntity.ok(
                ApiResponse.<List<SongResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách bài hát theo nghệ sĩ thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== GET SONGS BY GENRE =====
    @PreAuthorize("permitAll()")
    @GetMapping("/genre/{genreId}")
    public ResponseEntity<ApiResponse<List<SongResponse>>> getSongsByGenre(@PathVariable String genreId) {
        List<SongResponse> response = songService.getSongsByGenre(genreId);
        return ResponseEntity.ok(
                ApiResponse.<List<SongResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách bài hát theo thể loại thành công")
                        .data(response)
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/playlist/{playlistId}")
    public ResponseEntity<ApiResponse<List<SongResponse>>> getSongsByPlaylist(@PathVariable String playlistId) {
        List<SongResponse> songs = songService.getSongsByPlaylist(playlistId);
        return ResponseEntity.ok(
                ApiResponse.<List<SongResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách bài hát theo playlist thành công")
                        .data(songs)
                        .build()
        );
    }
}