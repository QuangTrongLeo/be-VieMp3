package be_viemp3.viemp3.mapper.music;

import be_viemp3.viemp3.dto.response.music.ArtistResponse;
import be_viemp3.viemp3.entity.Artist;

public class ArtistMapper {
    public static ArtistResponse toResponse(Artist artist) {
        ArtistResponse response = new ArtistResponse();
        response.setName(artist.getName());
        response.setAvatar(artist.getAvatar());
        return response;
    }
}
