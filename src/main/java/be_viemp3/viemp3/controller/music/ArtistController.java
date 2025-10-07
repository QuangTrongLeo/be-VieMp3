package be_viemp3.viemp3.controller.music;

import be_viemp3.viemp3.dto.request.music.artist.CreateAristRequest;
import be_viemp3.viemp3.dto.response.music.ArtistResponse;
import be_viemp3.viemp3.service.music.ArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${api.vie-mp3-url}/artists")
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createArtist(@RequestPart("name") String name, @RequestPart("avatar") MultipartFile avatar){
        CreateAristRequest request = new CreateAristRequest();
        request.setName(name);
        request.setAvatar(avatar);
        ArtistResponse response = artistService.createArtist(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> updateArtist(){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteArtist(){
        return ResponseEntity.ok().build();
    }
}
