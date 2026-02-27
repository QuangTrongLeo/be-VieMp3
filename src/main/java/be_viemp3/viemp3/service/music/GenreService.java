package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.dto.response.music.GenreResponse;
import be_viemp3.viemp3.entity.Genre;
import be_viemp3.viemp3.mapper.music.GenreMapper;
import be_viemp3.viemp3.repository.music.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    // GET LIST GENRE
    public List<GenreResponse> genres() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
                .map(GenreMapper::toResponse)
                .collect(Collectors.toList());
    }

    // GET GENRE BY ID
    public Genre findGenreById(UUID id) {
        return genreRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Genre không tồn tại với id: " + id));
    }
}
