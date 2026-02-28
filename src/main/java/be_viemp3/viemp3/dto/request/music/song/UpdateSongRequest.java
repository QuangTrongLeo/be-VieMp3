package be_viemp3.viemp3.dto.request.music.song;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateSongRequest {
    private String songId;
    private String title;
    private String description;
    private String genreId;
    private String albumId;
    private MultipartFile cover;
    private MultipartFile audio;
}
