package be_viemp3.viemp3.dto.request.music.song;

import lombok.Data;

@Data
public class AddSongToAlbumRequest {
    private String albumId;
    private String songId;
}