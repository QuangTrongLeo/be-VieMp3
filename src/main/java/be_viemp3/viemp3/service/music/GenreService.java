package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.dto.request.music.genre.CreateGenreRequest;
import be_viemp3.viemp3.dto.request.music.genre.UpdateGenreRequest;
import be_viemp3.viemp3.dto.response.music.GenreResponse;
import be_viemp3.viemp3.entity.Genre;
import be_viemp3.viemp3.enums.GenreEnum;
import be_viemp3.viemp3.mapper.music.GenreMapper;
import be_viemp3.viemp3.repository.music.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final EntityQueryService entityQueryService;

    // CREATE GENRE
    public GenreResponse createGenre(CreateGenreRequest request) {
        GenreEnum genreEnum = request.getName();
        if (genreRepository.existsByName(genreEnum)) {
            throw new IllegalArgumentException("Genre đã tồn tại: " + genreEnum);
        }
        Genre genre = new Genre();
        genre.setName(genreEnum);
        genreRepository.save(genre);
        return GenreMapper.toResponse(genre);
    }

    // GET LIST GENRE
    public List<GenreResponse> genres() {
        return genreRepository.findAll()
                .stream()
                .map(GenreMapper::toResponse)
                .collect(Collectors.toList());
    }

    // GET GENRE BY ID
    public GenreResponse getGenreById(String id) {
        return GenreMapper.toResponse(entityQueryService.findGenreById(id));
    }

    // UPDATE GENRE
    public GenreResponse updateGenre(UpdateGenreRequest request) {
        Genre genre = entityQueryService.findGenreById(request.getGenreId());
        GenreEnum newGenre = request.getName();
        if (!genre.getName().equals(newGenre) && genreRepository.existsByName(newGenre)) {
            throw new IllegalArgumentException("Genre đã tồn tại: " + newGenre);
        }
        genre.setName(newGenre);
        genreRepository.save(genre);
        return GenreMapper.toResponse(genre);
    }

    // DELETE GENRE
    public void deleteGenre(String id) {
        Genre genre = entityQueryService.findGenreById(id);
        if (genre.getSongs() != null && !genre.getSongs().isEmpty()) {
            throw new IllegalStateException("Không thể xóa Genre đang chứa bài hát");
        }
        genreRepository.delete(genre);
    }
}