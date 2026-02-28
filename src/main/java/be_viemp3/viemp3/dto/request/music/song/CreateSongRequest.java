package be_viemp3.viemp3.dto.request.music.song;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateSongRequest {
    private String title;
    private String description;
    private String artistId;
    private String genreId;
    private MultipartFile cover;
    private MultipartFile audio;
}
