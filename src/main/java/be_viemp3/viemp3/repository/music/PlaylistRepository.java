package be_viemp3.viemp3.repository.music;

import be_viemp3.viemp3.entity.Playlist;
import be_viemp3.viemp3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String> {
    List<Playlist> findByUser(User user);
}
