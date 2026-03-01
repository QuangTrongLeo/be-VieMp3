package be_viemp3.viemp3.mapper.music;

import be_viemp3.viemp3.dto.response.music.FavoriteAlbumResponse;
import be_viemp3.viemp3.entity.FavoriteAlbum;

import java.util.List;

public class FavoriteAlbumMapper {

    public static FavoriteAlbumResponse toResponse(FavoriteAlbum favoriteAlbum) {
        return FavoriteAlbumResponse.builder()
                .id(favoriteAlbum.getId())
                .favoritedAt(favoriteAlbum.getFavoritedAt())
                .album(AlbumMapper.toResponse(favoriteAlbum.getAlbum()))
                .build();
    }

    public static List<FavoriteAlbumResponse> toResponseList(List<FavoriteAlbum> list) {
        return list.stream()
                .map(FavoriteAlbumMapper::toResponse)
                .toList();
    }
}
