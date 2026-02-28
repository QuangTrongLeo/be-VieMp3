package be_viemp3.viemp3.dto.response.music;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SongResponse {
    private String id;
    private String title;
    private String cover;
    private String audio;
    private String description;
    private String artistId;
    private String albumId;
    private String genreId;
}
