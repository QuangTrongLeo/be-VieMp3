package be_viemp3.viemp3.dto.request.music.playlist;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PlaylistRequest {
    private String name;
    private MultipartFile cover;
}
