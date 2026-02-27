package be_viemp3.viemp3.controller.music;

import be_viemp3.viemp3.dto.response.music.GenreResponse;
import be_viemp3.viemp3.service.music.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${api.vie-mp3-url}/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<GenreResponse>> getAllGenres() {
        return ResponseEntity.ok(genreService.genres());
    }
}