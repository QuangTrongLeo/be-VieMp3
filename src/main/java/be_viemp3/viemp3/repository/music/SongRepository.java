package be_viemp3.viemp3.repository.music;

import be_viemp3.viemp3.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, String> {
    List<Song> findByArtistId(String artistId);
    List<Song> findByAlbumId(String albumId);
}
