package be_viemp3.viemp3.controller.music;

import be_viemp3.viemp3.common.response.ApiResponse;
import be_viemp3.viemp3.dto.request.music.song.AddSongToAlbumRequest;
import be_viemp3.viemp3.dto.request.music.song.CreateSongRequest;
import be_viemp3.viemp3.dto.request.music.song.UpdateSongRequest;
import be_viemp3.viemp3.dto.response.music.SongResponse;
import be_viemp3.viemp3.service.music.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.vie-mp3-url}/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    // ===== CREATE =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<SongResponse>> createSong(@ModelAttribute CreateSongRequest request) {
        SongResponse response = songService.createSong(request);
        return ResponseEntity.ok(
                ApiResponse.<SongResponse>builder()
                        .success(true)
                        .message("Tạo bài hát thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== ADD SONG TO ALBUM =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @PutMapping("/album")
    public ResponseEntity<ApiResponse<Void>> addSongToAlbum(@RequestBody AddSongToAlbumRequest request) {
        songService.addSongToAlbum(request);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Thêm bài hát vào album thành công")
                        .build()
        );
    }

    // ===== UPDATE =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<SongResponse>> updateSong(@ModelAttribute UpdateSongRequest request) {
        SongResponse response = songService.updateSong(request);
        return ResponseEntity.ok(
                ApiResponse.<SongResponse>builder()
                        .success(true)
                        .message("Cập nhật bài hát thành công")
                        .data(response)
                        .build()
        );
    }

    // ===== DELETE =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteSong(@RequestParam UUID songId) {
        songService.deleteSong(songId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Xóa bài hát thành công")
                        .build()
        );
    }

    // ===== REMOVE SONG FROM ALBUM =====
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @DeleteMapping("/{songId}/album")
    public ResponseEntity<ApiResponse<Void>> removeSongFromAlbum(@PathVariable UUID songId) {
        songService.removeSongFromAlbum(songId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Đã xóa bài hát khỏi album")
                        .build()
        );
    }

    // ===== GET BY ID =====
    @PreAuthorize("permitAll()")
    @GetMapping("/{songId}")
    public ResponseEntity<ApiResponse<SongResponse>> getSongById(@PathVariable UUID songId) {
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

    // ===== GET ALL SONG BY ALBUM =====
    @PreAuthorize("permitAll()")
    @GetMapping("/album/{albumId}")
    public ResponseEntity<ApiResponse<List<SongResponse>>> getSongsByAlbum(@PathVariable UUID albumId) {
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
    public ResponseEntity<ApiResponse<List<SongResponse>>> getSongsByArtist(@PathVariable UUID artistId) {
        List<SongResponse> response = songService.getSongsByArtist(artistId);
        return ResponseEntity.ok(
                ApiResponse.<List<SongResponse>>builder()
                        .success(true)
                        .message("Lấy danh sách bài hát theo nghệ sĩ thành công")
                        .data(response)
                        .build()
        );
    }
}