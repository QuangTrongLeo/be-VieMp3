package be_viemp3.viemp3.dto.response.music;

import lombok.Data;

@Data
public class ArtistResponse {
    private String id;
    private String name;
    private String avatar;
    private int followers;
}
