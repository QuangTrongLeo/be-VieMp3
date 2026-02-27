package be_viemp3.viemp3.controller.music;

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

    // CREATE ARTIST
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArtistResponse> createArtist(@ModelAttribute CreateAristRequest request) {
        return ResponseEntity.ok(artistService.createArtist(request));
    }

    // UPDATE ARTIST
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArtistResponse> updateArtist(@ModelAttribute UpdateArtistRequest request) {
        return ResponseEntity.ok(artistService.updateArtist(request));
    }

    // DELETE ARTIST
    @PreAuthorize("hasAnyRole('ADMIN','MOD')")
    @DeleteMapping
    public ResponseEntity<String> deleteArtist(@RequestParam("artistId") UUID artistId) {
        artistService.deleteArtistById(artistId);
        return ResponseEntity.ok("Xóa nghệ sĩ thành công!");
    }

    // GET ARTIST BY NAME
    @PreAuthorize("permitAll()")
    @GetMapping("/{artistName}")
    public ResponseEntity<ArtistResponse> getArtistByName(@PathVariable String artistName) {
        return ResponseEntity.ok(artistService.getArtistByName(artistName));
    }

    // GET ALL ARTIST
    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }
}