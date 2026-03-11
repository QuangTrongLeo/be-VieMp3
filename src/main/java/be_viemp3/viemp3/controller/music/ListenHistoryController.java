package be_viemp3.viemp3.controller.music;

import be_viemp3.viemp3.common.response.ApiResponse;
import be_viemp3.viemp3.dto.response.music.ListenHistoryResponse;
import be_viemp3.viemp3.service.music.ListenHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.vie-mp3-url}/listen-histories")
@RequiredArgsConstructor
public class ListenHistoryController {
    private final ListenHistoryService listenHistoryService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<ListenHistoryResponse>>> getMyListenHistory() {
        List<ListenHistoryResponse> responses = listenHistoryService.getMyListenHistory();
        return ResponseEntity.ok(
                ApiResponse.<List<ListenHistoryResponse>>builder()
                        .success(true)
                        .message("Lấy lịch sử nghe nhạc thành công")
                        .data(responses)
                        .build()
        );
    }
}
