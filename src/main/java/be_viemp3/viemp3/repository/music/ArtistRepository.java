package be_viemp3.viemp3.repository.music;

import be_viemp3.viemp3.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findByName(String name);
}
