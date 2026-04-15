package be_viemp3.viemp3.service.subscription;

import be_viemp3.viemp3.service.auth.SecurityService;
import be_viemp3.viemp3.dto.response.subscription.NotificationResponse;
import be_viemp3.viemp3.entity.*;
import be_viemp3.viemp3.mapper.subscription.NotificationMapper;
import be_viemp3.viemp3.repository.music.FavoriteArtistRepository;
import be_viemp3.viemp3.repository.subscription.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final FavoriteArtistRepository favoriteArtistRepository;
    private final SecurityService securityService;

    public List<NotificationResponse> getMyNotifications() {
        User user = securityService.getCurrentUser();
        List<Notification> notifications = notificationRepository.findByUserIdOrderByNotificationAtDesc(user.getId());
        return NotificationMapper.toResponseList(notifications);
    }

    public void notifyNewSong(Artist artist, Song song) {
        List<FavoriteArtist> favorites = favoriteArtistRepository.findByArtistId(artist.getId());
        for (FavoriteArtist favorite : favorites) {
            Notification notification = new Notification();
            notification.setUser(favorite.getUser());
            notification.setTitle(artist.getName() + " vừa ra mắt bài hát \"" + song.getTitle() + "\"");
            notification.setCover(song.getCover());
            notificationRepository.save(notification);
        }
    }

    public void notifyNewAlbum(Artist artist, Album album) {
        List<FavoriteArtist> favorites = favoriteArtistRepository.findByArtistId(artist.getId());
        for (FavoriteArtist favorite : favorites) {
            Notification notification = new Notification();
            notification.setUser(favorite.getUser());
            notification.setTitle(artist.getName() + " vừa ra mắt album \"" + album.getTitle() + "\"");
            notification.setCover(album.getCover());
            notificationRepository.save(notification);
        }
    }

}
