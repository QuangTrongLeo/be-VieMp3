package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.common.util.SecurityUtils;
import be_viemp3.viemp3.dto.response.music.ListenHistoryResponse;
import be_viemp3.viemp3.entity.ListenHistory;
import be_viemp3.viemp3.entity.Song;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.mapper.music.ListenHistoryMapper;
import be_viemp3.viemp3.repository.music.ListenHistoryRepository;
import be_viemp3.viemp3.repository.music.SongRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListenHistoryService {

    private final ListenHistoryRepository listenHistoryRepository;
    private final EntityQueryService entityQueryService;
    private final SecurityUtils securityUtils;
    private final SongRepository songRepository;
    private static final int MAX_HISTORY = 30;

    public List<ListenHistoryResponse> getMyListenHistory() {
        User user = securityUtils.getCurrentUser();
        List<ListenHistory> histories = listenHistoryRepository.findByUserIdOrderByListenedAtDesc(user.getId());
        return ListenHistoryMapper.toResponseList(histories);
    }

    @Transactional
    public void saveListenHistory(String songId) {
        User user = securityUtils.getCurrentUser();
        Song song = entityQueryService.findSongById(songId);
        Optional<ListenHistory> optional = listenHistoryRepository.findByUserIdAndSongId(user.getId(), songId);
        if (optional.isPresent()) {
            ListenHistory history = optional.get();
            history.setListenedAt(OffsetDateTime.now());
            listenHistoryRepository.save(history);
        } else {
            ListenHistory history = new ListenHistory();
            history.setUser(user);
            history.setSong(song);
            listenHistoryRepository.save(history);

            // Giới hạn 30 bản ghi
            long count = listenHistoryRepository.countByUserId(user.getId());
            if (count > MAX_HISTORY) {
                List<ListenHistory> histories = listenHistoryRepository.findByUserIdOrderByListenedAtAsc(user.getId());
                int needDelete = (int) (count - MAX_HISTORY);
                for (int i = 0; i < needDelete; i++) {
                    listenHistoryRepository.deleteById(histories.get(i).getId());
                }
            }
        }

        songRepository.incrementListenCount(songId);
    }
}
