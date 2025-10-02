package be_viemp3.viemp3.controller.music;

import be_viemp3.viemp3.dto.response.music.GenreResponse;
import be_viemp3.viemp3.service.song.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.vie-mp3-url}/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<?> genres() {
        try {
            List<GenreResponse> genres = genreService.genres();
            return ResponseEntity.ok(genres);
        } catch (Exception e) {
            e.printStackTrace();
            // có thể trả về message hoặc status code phù hợp
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi lấy danh sách thể loại: " + e.getMessage());
        }
    }
}
