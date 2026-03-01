package be_viemp3.viemp3.repository.music;

import be_viemp3.viemp3.entity.FavoriteAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteAlbumRepository extends JpaRepository<FavoriteAlbum, String> {
    boolean existsByUserIdAndAlbumId(String userId, String albumId);
    Optional<FavoriteAlbum> findByUserIdAndAlbumId(String userId, String albumId);
    List<FavoriteAlbum> findByUserId(String userId);
}
