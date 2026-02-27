package be_viemp3.viemp3.repository.music;

import be_viemp3.viemp3.entity.Genre;
import be_viemp3.viemp3.enums.GenreEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {
    boolean existsByName(GenreEnum genre);
}
