package be_viemp3.viemp3.repository.music;

import be_viemp3.viemp3.entity.FollowArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteArtistRepository extends JpaRepository<FollowArtist, Long> {
}
