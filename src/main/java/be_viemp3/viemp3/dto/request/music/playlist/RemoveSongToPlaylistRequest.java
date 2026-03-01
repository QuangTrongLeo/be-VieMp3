package be_viemp3.viemp3.dto.request.music.playlist;

import lombok.Data;

@Data
public class RemoveSongToPlaylistRequest {
    private String playlistId;
    private String songId;
}
