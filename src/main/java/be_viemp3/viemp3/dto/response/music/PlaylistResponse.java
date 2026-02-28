package be_viemp3.viemp3.dto.response.music;

import lombok.Data;
import java.util.List;

@Data
public class PlaylistResponse {
    private String id;
    private String name;
    private String cover;
    private String userId;
    private List<String> songIds;
}