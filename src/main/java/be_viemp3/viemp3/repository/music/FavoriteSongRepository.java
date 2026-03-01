package be_viemp3.viemp3.repository.music;

import be_viemp3.viemp3.entity.FavoriteSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteSongRepository extends JpaRepository<FavoriteSong, String> {
    boolean existsByUserIdAndSongId(String userId, String songId);
    Optional<FavoriteSong> findByUserIdAndSongId(String userId, String songId);
    List<FavoriteSong> findByUserId(String userId);
}
