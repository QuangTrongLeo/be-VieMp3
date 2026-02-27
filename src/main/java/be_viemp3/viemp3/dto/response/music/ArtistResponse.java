package be_viemp3.viemp3.dto.response.music;

import lombok.Data;

import java.util.UUID;

@Data
public class ArtistResponse {
    private UUID id;
    private String name;
    private String avatar;
    private int followers;
}
