package be_viemp3.viemp3.dto.request.music.artist;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class UpdateArtistRequest {
    private UUID artistId;
    private String artistName;
    private MultipartFile avatar;
}