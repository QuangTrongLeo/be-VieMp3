package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.dto.request.music.genre.GenreRequest;
import be_viemp3.viemp3.dto.response.music.GenreResponse;
import be_viemp3.viemp3.entity.Genre;
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
    public GenreResponse createGenre(GenreRequest request) {
        String name = request.getName().trim().toUpperCase();
        if (genreRepository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Genre đã tồn tại: " + name);
        }
        Genre genre = new Genre();
        genre.setName(name);
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
    public GenreResponse updateGenre(String id, GenreRequest request) {
        Genre genre = entityQueryService.findGenreById(id);
        String newName = request.getName().trim().toUpperCase();
        if (!genre.getName().equalsIgnoreCase(newName)
                && genreRepository.existsByNameIgnoreCase(newName)) {
            throw new IllegalArgumentException("Genre đã tồn tại: " + newName);
        }
        genre.setName(newName);
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