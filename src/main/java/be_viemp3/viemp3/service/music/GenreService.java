package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.dto.response.music.GenreResponse;
import be_viemp3.viemp3.entity.Genre;
import be_viemp3.viemp3.mapper.music.GenreMapper;
import be_viemp3.viemp3.repository.music.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    // DANH SÁCH THỂ LOẠI NHẠC
    public List<GenreResponse> genres() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
                .map(GenreMapper::toResponse)
                .collect(Collectors.toList());
    }
}
