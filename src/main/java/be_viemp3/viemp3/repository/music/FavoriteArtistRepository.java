package be_viemp3.viemp3.repository.music;

import be_viemp3.viemp3.entity.FavoriteArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteArtistRepository extends JpaRepository<FavoriteArtist, String> {
    boolean existsByUserIdAndArtistId(String userId, String artistId);
    Optional<FavoriteArtist> findByUserIdAndArtistId(String userId, String artistId);
    List<FavoriteArtist> findByUserId(String userId);
}
