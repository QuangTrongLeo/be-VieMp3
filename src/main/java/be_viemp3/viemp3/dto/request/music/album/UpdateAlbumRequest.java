package be_viemp3.viemp3.dto.request.music.album;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class UpdateAlbumRequest {
    private UUID albumId;
    private String title;
    private MultipartFile cover;
}