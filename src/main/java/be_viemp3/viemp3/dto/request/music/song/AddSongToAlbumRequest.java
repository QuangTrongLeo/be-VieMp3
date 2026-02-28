package be_viemp3.viemp3.dto.request.music.song;

import lombok.Data;
import java.util.UUID;

@Data
public class AddSongToAlbumRequest {
    private UUID albumId;
    private UUID songId;
}