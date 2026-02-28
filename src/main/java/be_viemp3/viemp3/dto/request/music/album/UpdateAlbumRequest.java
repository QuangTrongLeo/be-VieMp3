package be_viemp3.viemp3.dto.request.music.album;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateAlbumRequest {
    private String albumId;
    private String title;
    private MultipartFile cover;
}