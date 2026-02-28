package be_viemp3.viemp3.mapper.music;

import be_viemp3.viemp3.dto.response.music.PlaylistResponse;
import be_viemp3.viemp3.entity.Playlist;
import java.util.List;
import java.util.stream.Collectors;

public class PlaylistMapper {
    public static PlaylistResponse toResponse(Playlist playlist) {
        if (playlist == null) return null;
        PlaylistResponse response = new PlaylistResponse();
        response.setId(playlist.getId());
        response.setName(playlist.getName());
        response.setCover(playlist.getCover());
        if (playlist.getUser() != null) {
            response.setUserId(playlist.getUser().getId());
        }
        if (playlist.getSongs() != null) {
            response.setSongIds(
                    playlist.getSongs()
                            .stream()
                            .map(song -> song.getId())
                            .collect(Collectors.toList())
            );
        }
        return response;
    }

    public static List<PlaylistResponse> toResponseList(List<Playlist> playlists) {
        if (playlists == null) return List.of();
        return playlists.stream()
                .map(PlaylistMapper::toResponse)
                .collect(Collectors.toList());
    }
}
