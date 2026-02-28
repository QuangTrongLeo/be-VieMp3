package be_viemp3.viemp3.dto.response.music;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlbumResponse {
    private String id;
    private String title;
    private String cover;
    private String artistId;
}