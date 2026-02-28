package be_viemp3.viemp3.dto.request.music.artist;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateArtistRequest {
    private String artistId;
    private String artistName;
    private MultipartFile avatar;
}