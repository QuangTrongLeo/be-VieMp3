package be_viemp3.viemp3.dto.response.music;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SongResponse {
    private UUID id;
    private String title;
    private String cover;
    private String audio;
    private String description;
    private UUID artistId;
    private UUID albumId;
    private UUID genreId;
}
