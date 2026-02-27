package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.entity.Album;
import be_viemp3.viemp3.repository.music.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;

    public Album findAlbumById(UUID id) {
        return albumRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Album không tồn tại"));
    }
}
