package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.service.auth.SecurityService;
import be_viemp3.viemp3.dto.response.music.FavoriteArtistResponse;
import be_viemp3.viemp3.entity.Artist;
import be_viemp3.viemp3.entity.FavoriteArtist;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.mapper.music.FavoriteArtistMapper;
import be_viemp3.viemp3.repository.music.ArtistRepository;
import be_viemp3.viemp3.repository.music.FavoriteArtistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteArtistService {
    private final ArtistRepository artistRepository;
    private final FavoriteArtistRepository favoriteArtistRepository;
    private final EntityQueryService entityQueryService;
    private final SecurityService securityService;

    // ===== ADD ARTIST TO FAVORITE =====
    @Transactional
    public void addArtistToFavorite(String artistId) {
        User currentUser = securityService.getCurrentUser();
        Artist artist = entityQueryService.findArtistById(artistId);
        boolean exists = favoriteArtistRepository.existsByUserIdAndArtistId(currentUser.getId(), artistId);
        if (exists) return;
        FavoriteArtist favoriteArtist = new FavoriteArtist();
        favoriteArtist.setUser(currentUser);
        favoriteArtist.setArtist(artist);
        favoriteArtistRepository.save(favoriteArtist);
        artistRepository.incrementFavorites(artistId);
    }

    // ===== REMOVE ARTIST FROM FAVORITE =====
    @Transactional
    public void removeArtistFromFavorite(String artistId) {
        User currentUser = securityService.getCurrentUser();
        FavoriteArtist favoriteArtist = entityQueryService.findFavoriteArtist(currentUser.getId(), artistId);
        favoriteArtistRepository.delete(favoriteArtist);
        artistRepository.decrementFavorites(artistId);
    }

    // ===== GET MY FAVORITE ARTISTS =====
    public List<FavoriteArtistResponse> getMyFavoriteArtists() {
        User currentUser = securityService.getCurrentUser();
        List<FavoriteArtist> favoriteArtists = favoriteArtistRepository.findByUserId(currentUser.getId());
        return FavoriteArtistMapper.toResponseList(favoriteArtists);
    }
}
