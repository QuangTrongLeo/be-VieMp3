package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.common.util.SecurityUtils;
import be_viemp3.viemp3.dto.response.music.ListenHistoryResponse;
import be_viemp3.viemp3.entity.ListenHistory;
import be_viemp3.viemp3.entity.Song;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.mapper.music.ListenHistoryMapper;
import be_viemp3.viemp3.repository.music.ListenHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListenHistoryService {

    private final ListenHistoryRepository listenHistoryRepository;
    private final EntityQueryService entityQueryService;
    private final SecurityUtils securityUtils;

    public List<ListenHistoryResponse> getMyListenHistory() {
        User user = securityUtils.getCurrentUser();
        List<ListenHistory> histories = listenHistoryRepository.findByUserIdOrderByListenedAtDesc(user.getId());
        return ListenHistoryMapper.toResponseList(histories);
    }

    public void saveListenHistory(String songId) {
        User user = securityUtils.getCurrentUser();
        Song song = entityQueryService.findSongById(songId);
        ListenHistory history = new ListenHistory();
        history.setUser(user);
        history.setSong(song);
        listenHistoryRepository.save(history);
    }
}
