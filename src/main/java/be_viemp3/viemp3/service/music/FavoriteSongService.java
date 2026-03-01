package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.common.util.SecurityUtils;
import be_viemp3.viemp3.dto.response.music.FavoriteSongResponse;
import be_viemp3.viemp3.entity.FavoriteSong;
import be_viemp3.viemp3.entity.Song;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.mapper.music.FavoriteSongMapper;
import be_viemp3.viemp3.repository.music.FavoriteSongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteSongService {
    private final FavoriteSongRepository favoriteSongRepository;
    private final EntityQueryService entityQueryService;
    private final SecurityUtils securityUtils;

    // ===== ADD SONG TO FAVORITE =====
    public void addSongToFavorite(String songId) {
        User currentUser = securityUtils.getCurrentUser();
        Song song = entityQueryService.findSongById(songId);
        boolean exists = favoriteSongRepository.existsByUserIdAndSongId(currentUser.getId(), songId);
        if (exists) {
            return; // đã tồn tại thì không làm gì
        }
        FavoriteSong favoriteSong = new FavoriteSong();
        favoriteSong.setUser(currentUser);
        favoriteSong.setSong(song);
        favoriteSongRepository.save(favoriteSong);
    }

    // ===== REMOVE SONG FROM FAVORITE =====
    public void removeSongFromFavorite(String songId) {
        User currentUser = securityUtils.getCurrentUser();
        FavoriteSong favoriteSong = favoriteSongRepository
                .findByUserIdAndSongId(currentUser.getId(), songId)
                .orElseThrow(() ->
                        new IllegalStateException("Bài hát không tồn tại trong danh sách yêu thích")
                );
        favoriteSongRepository.delete(favoriteSong);
    }

    // ===== GET MY FAVORITE SONGS =====
    public List<FavoriteSongResponse> getMyFavoriteSongs() {
        User currentUser = securityUtils.getCurrentUser();
        List<FavoriteSong> favoriteSongs = favoriteSongRepository.findByUserId(currentUser.getId());
        return FavoriteSongMapper.toResponseList(favoriteSongs);
    }
}
