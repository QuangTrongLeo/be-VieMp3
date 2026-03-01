package be_viemp3.viemp3.controller.music;

import be_viemp3.viemp3.dto.response.music.ArtistResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteArtistResponse {
    private String id;
    private OffsetDateTime favoritedAt;
    private ArtistResponse artist;
}
