package be_viemp3.viemp3.dto.request.music.song;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Data
public class UpdateSongRequest {
    private UUID songId;
    private String title;
    private String description;
    private UUID genreId;
    private UUID albumId;
    private MultipartFile cover;
    private MultipartFile audio;
}
