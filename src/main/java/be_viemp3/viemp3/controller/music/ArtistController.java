package be_viemp3.viemp3.controller.music;

import be_viemp3.viemp3.dto.request.music.artist.CreateAristRequest;
import be_viemp3.viemp3.dto.request.music.artist.UpdateArtistAvatarRequest;
import be_viemp3.viemp3.dto.request.music.artist.UpdateArtistNameRequest;
import be_viemp3.viemp3.dto.response.music.ArtistResponse;
import be_viemp3.viemp3.service.music.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("${api.vie-mp3-url}/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    // CREATE ARTIST
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArtistResponse> createArtist(@RequestPart("name") String name, @RequestPart("avatar") MultipartFile avatar) {
        CreateAristRequest request = new CreateAristRequest();
        request.setName(name);
        request.setAvatar(avatar);
        return ResponseEntity.ok(artistService.createArtist(request));
    }

    // UPDATE ARTIST NAME
    @PutMapping("/name")
    public ResponseEntity<ArtistResponse> updateArtistName(@RequestBody UpdateArtistNameRequest request) {
        return ResponseEntity.ok(artistService.updateArtistName(request));
    }

    // UPDATE ARTIST AVATAR
    @PutMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArtistResponse> updateArtistAvatar(
            @RequestParam("artistId") Long artistId,
            @RequestParam("avatar") MultipartFile avatar) {
        UpdateArtistAvatarRequest request = new UpdateArtistAvatarRequest();
        request.setArtistId(artistId);
        request.setAvatar(avatar);
        return ResponseEntity.ok(artistService.updateArtistAvatar(request));
    }

    // DELETE ARTIST
    @DeleteMapping
    public ResponseEntity<String> deleteArtist(@RequestParam("artistId") Long artistId) {
        artistService.deleteArtistById(artistId);
        return ResponseEntity.ok("Xóa nghệ sĩ thành công!");
    }

    // GET ARTIST BY NAME
    @GetMapping("/{artistName}")
    public ResponseEntity<ArtistResponse> getArtistByName(@PathVariable String artistName) {
        return ResponseEntity.ok(artistService.getArtistByName(artistName));
    }

    // GET ALL ARTIST
    @GetMapping("/all")
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }
}