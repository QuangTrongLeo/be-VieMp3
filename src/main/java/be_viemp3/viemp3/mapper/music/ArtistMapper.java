package be_viemp3.viemp3.mapper.music;

import be_viemp3.viemp3.dto.response.music.ArtistResponse;
import be_viemp3.viemp3.entity.Artist;

import java.util.List;
import java.util.stream.Collectors;

public class ArtistMapper {
    public static ArtistResponse toResponse(Artist artist) {
        ArtistResponse response = new ArtistResponse();
        response.setName(artist.getName());
        response.setAvatar(artist.getAvatar());

        int actualFollowers = (artist.getFollowArtists() != null) ? artist.getFollowArtists().size() : 0;
        response.setFollowers(100000 + actualFollowers);

        return response;
    }

    public static List<ArtistResponse> toResponseList(List<Artist> artists) {
        return artists.stream()
                .map(ArtistMapper::toResponse)
                .collect(Collectors.toList());
    }
}
