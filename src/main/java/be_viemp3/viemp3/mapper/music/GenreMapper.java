package be_viemp3.viemp3.mapper.music;

import be_viemp3.viemp3.dto.response.music.GenreResponse;
import be_viemp3.viemp3.entity.Genre;

public class GenreMapper {
    public static GenreResponse toResponse(Genre genre) {
        GenreResponse response = new GenreResponse();
        response.setId(genre.getId());
        response.setName(genre.getName().name());             // Enum code
        response.setDescription(genre.getName().getDescription()); // Enum description
        return response;
    }
}
