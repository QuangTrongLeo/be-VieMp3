package be_viemp3.viemp3.controller.music;

import be_viemp3.viemp3.dto.request.music.artist.CreateAristRequest;
import be_viemp3.viemp3.dto.request.music.artist.UpdateArtistAvatarRequest;
import be_viemp3.viemp3.dto.request.music.artist.UpdateArtistNameRequest;
import be_viemp3.viemp3.dto.response.music.ArtistResponse;
import be_viemp3.viemp3.service.music.ArtistService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${api.vie-mp3-url}/artists")
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    // CREATE ARTIST
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createArtist(@RequestPart("name") String name,
                                          @RequestPart("avatar") MultipartFile avatar){
        CreateAristRequest request = new CreateAristRequest();
        request.setName(name);
        request.setAvatar(avatar);
        ArtistResponse response = artistService.createArtist(request);
        return ResponseEntity.ok(response);
    }

    // UPDATE ARTIST NAME
    @PutMapping("/name")
    public ResponseEntity<?> updateArtistName(@RequestBody UpdateArtistNameRequest request) {
        try {
            ArtistResponse response = artistService.updateArtistName(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Lỗi nghiệp vụ (ví dụ: artist không tồn tại)
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Lỗi không lường trước
            return ResponseEntity.internalServerError()
                    .body("Đã xảy ra lỗi khi cập nhật nghệ sĩ: " + e.getMessage());
        }
    }

    // UPDATE ARTIST AVATAR
    @PutMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateArtistAvatar(
            @RequestParam("artistId") Long artistId,
            @RequestParam("avatar") MultipartFile avatar) {
        try {
            UpdateArtistAvatarRequest request = new UpdateArtistAvatarRequest();
            request.setArtistId(artistId);
            request.setAvatar(avatar);

            ArtistResponse response = artistService.updateArtistAvatar(request);
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Đã xảy ra lỗi khi cập nhật avatar: " + e.getMessage());
        }
    }
    
    // DELETE ARTIST
    @DeleteMapping
    public ResponseEntity<?> deleteArtist(@RequestParam("artistId") Long artistId) {
        try {
            artistService.deleteArtistById(artistId);
            return ResponseEntity.ok("Xóa nghệ sĩ thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Đã xảy ra lỗi không xác định: " + e.getMessage());
        }
    }

    // GET ARTIST BY NAME
    @GetMapping("/{artistName}")
    public ResponseEntity<?> getArtistByName(@PathVariable String artistName) {
        try {
            ArtistResponse response = artistService.getArtistByName(artistName);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Đã xảy ra lỗi khi lấy thông tin nghệ sĩ: " + e.getMessage());
        }
    }

    // GET ALL ARTIST
    @GetMapping("/all")
    public ResponseEntity<?> getAllArtists() {
        try {
            List<ArtistResponse> artists = artistService.getAllArtists();
            return ResponseEntity.ok(artists);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Đã xảy ra lỗi khi lấy danh sách nghệ sĩ: " + e.getMessage());
        }
    }
}
