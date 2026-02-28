package be_viemp3.viemp3.mapper.music;

import be_viemp3.viemp3.dto.response.music.SongResponse;
import be_viemp3.viemp3.entity.Song;

import java.util.List;
import java.util.stream.Collectors;

public class SongMapper {

    public static SongResponse toResponse(Song song) {
        if (song == null) {
            return null;
        }

        return SongResponse.builder()
                .id(song.getId())
                .title(song.getTitle())
                .cover(song.getCover())
                .audio(song.getAudio())
                .description(song.getDescription())
                .artistId(song.getArtist() != null ? song.getArtist().getId() : null)
                .albumId(song.getAlbum() != null ? song.getAlbum().getId() : null)
                .genreId(song.getGenre() != null ? song.getGenre().getId() : null)
                .build();
    }

    public static List<SongResponse> toResponseList(List<Song> songs) {
        if (songs == null) {
            return List.of();
        }

        return songs.stream()
                .map(SongMapper::toResponse)
                .collect(Collectors.toList());
    }
}