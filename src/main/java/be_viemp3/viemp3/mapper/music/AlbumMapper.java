package be_viemp3.viemp3.mapper.music;

import be_viemp3.viemp3.dto.response.music.AlbumResponse;
import be_viemp3.viemp3.entity.Album;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumMapper {
    public static AlbumResponse toResponse(Album album) {
        return AlbumResponse.builder()
                .id(album.getId())
                .title(album.getTitle())
                .cover(album.getCover())
                .artistId(album.getArtist().getId())
                .build();
    }

    public static List<AlbumResponse> toResponseList(List<Album> albums) {
        return albums.stream()
                .map(AlbumMapper::toResponse)
                .collect(Collectors.toList());
    }
}