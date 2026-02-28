package be_viemp3.viemp3.repository.music;

import be_viemp3.viemp3.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SongRepository extends JpaRepository<Song, UUID> {
    List<Song> findByArtistId(UUID artistId);
    List<Song> findByAlbumId(UUID albumId);
}
