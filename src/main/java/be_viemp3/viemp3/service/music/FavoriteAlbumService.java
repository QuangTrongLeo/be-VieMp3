package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.common.util.SecurityUtils;
import be_viemp3.viemp3.dto.response.music.FavoriteAlbumResponse;
import be_viemp3.viemp3.entity.Album;
import be_viemp3.viemp3.entity.FavoriteAlbum;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.mapper.music.FavoriteAlbumMapper;
import be_viemp3.viemp3.repository.music.FavoriteAlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteAlbumService {
    private final FavoriteAlbumRepository favoriteAlbumRepository;
    private final EntityQueryService entityQueryService;
    private final SecurityUtils securityUtils;

    // ===== ADD ALBUM TO FAVORITE =====
    public void addAlbumToFavorite(String albumId) {
        User currentUser = securityUtils.getCurrentUser();
        Album album = entityQueryService.findAlbumById(albumId);
        boolean exists = favoriteAlbumRepository.existsByUserIdAndAlbumId(currentUser.getId(), albumId);
        if (exists) {
            return;
        }
        FavoriteAlbum favoriteAlbum = new FavoriteAlbum();
        favoriteAlbum.setUser(currentUser);
        favoriteAlbum.setAlbum(album);
        favoriteAlbumRepository.save(favoriteAlbum);
    }

    // ===== REMOVE ALBUM FROM FAVORITE =====
    public void removeAlbumFromFavorite(String albumId) {
        User currentUser = securityUtils.getCurrentUser();
        FavoriteAlbum favoriteAlbum = favoriteAlbumRepository
                .findByUserIdAndAlbumId(currentUser.getId(), albumId)
                .orElseThrow(() ->
                        new IllegalStateException("Album không tồn tại trong danh sách yêu thích")
                );
        favoriteAlbumRepository.delete(favoriteAlbum);
    }

    // ===== GET MY FAVORITE ALBUMS =====
    public List<FavoriteAlbumResponse> getMyFavoriteAlbums() {
        User currentUser = securityUtils.getCurrentUser();
        List<FavoriteAlbum> favoriteAlbums = favoriteAlbumRepository.findByUserId(currentUser.getId());
        return FavoriteAlbumMapper.toResponseList(favoriteAlbums);
    }
}
