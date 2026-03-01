package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.common.util.SecurityUtils;
import be_viemp3.viemp3.controller.music.FavoriteArtistResponse;
import be_viemp3.viemp3.entity.Artist;
import be_viemp3.viemp3.entity.FavoriteArtist;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.mapper.music.FavoriteArtistMapper;
import be_viemp3.viemp3.repository.music.FavoriteArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteArtistService {
    private final FavoriteArtistRepository favoriteArtistRepository;
    private final EntityQueryService entityQueryService;
    private final SecurityUtils securityUtils;

    // ===== ADD ARTIST TO FAVORITE =====
    public void addArtistToFavorite(String artistId) {
        User currentUser = securityUtils.getCurrentUser();
        Artist artist = entityQueryService.findArtistById(artistId);
        boolean exists = favoriteArtistRepository.existsByUserIdAndArtistId(currentUser.getId(), artistId);
        if (exists) return;
        FavoriteArtist favoriteArtist = new FavoriteArtist();
        favoriteArtist.setUser(currentUser);
        favoriteArtist.setArtist(artist);
        favoriteArtistRepository.save(favoriteArtist);
    }

    // ===== REMOVE ARTIST FROM FAVORITE =====
    public void removeArtistFromFavorite(String artistId) {
        User currentUser = securityUtils.getCurrentUser();
        FavoriteArtist favoriteArtist = favoriteArtistRepository
                .findByUserIdAndArtistId(currentUser.getId(), artistId)
                .orElseThrow(() ->
                        new IllegalStateException("Artist không tồn tại trong danh sách yêu thích")
                );

        favoriteArtistRepository.delete(favoriteArtist);
    }

    // ===== GET MY FAVORITE ARTISTS =====
    public List<FavoriteArtistResponse> getMyFavoriteArtists() {
        User currentUser = securityUtils.getCurrentUser();
        List<FavoriteArtist> favoriteArtists = favoriteArtistRepository.findByUserId(currentUser.getId());
        return FavoriteArtistMapper.toResponseList(favoriteArtists);
    }
}
