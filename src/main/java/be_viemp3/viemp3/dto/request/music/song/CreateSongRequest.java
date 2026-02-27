package be_viemp3.viemp3.dto.request.music.song;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Data
public class CreateSongRequest {
    private String title;
    private String description;
    private UUID artistId;
    private UUID albumId;
    private UUID genreId;
    private MultipartFile cover;
    private MultipartFile audio;
}
