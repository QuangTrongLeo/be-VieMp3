package be_viemp3.viemp3.dto.response.music;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AlbumResponse {
    private UUID id;
    private String title;
    private String cover;
    private UUID artistId;
}