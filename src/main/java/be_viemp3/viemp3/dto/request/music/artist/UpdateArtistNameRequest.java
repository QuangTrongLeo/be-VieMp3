package be_viemp3.viemp3.dto.request.music.artist;

import lombok.Data;

@Data
public class UpdateArtistNameRequest {
    private Long artistId;
    private String artistName;
}
