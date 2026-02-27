package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.dto.request.music.artist.CreateAristRequest;
import be_viemp3.viemp3.dto.request.music.artist.UpdateArtistRequest;
import be_viemp3.viemp3.dto.response.music.ArtistResponse;
import be_viemp3.viemp3.entity.Artist;
import be_viemp3.viemp3.mapper.music.ArtistMapper;
import be_viemp3.viemp3.repository.music.ArtistRepository;
import be_viemp3.viemp3.service.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final FileStorageService fileStorageService;

    // ===== CREATE =====
    public ArtistResponse createArtist(CreateAristRequest request) {
        String avatarUrl = fileStorageService.upload(request.getAvatar(), "artists");
        Artist artist = new Artist();
        artist.setName(request.getName().trim());
        artist.setAvatar(avatarUrl);
        artistRepository.save(artist);
        return ArtistMapper.toResponse(artist);
    }

    // ===== UPDATE =====
    public ArtistResponse updateArtist(UpdateArtistRequest request) {
        Artist artist = findArtistById(request.getArtistId());
        boolean isUpdated = false;

        // ===== UPDATE NAME =====
        if (request.getArtistName() != null && !request.getArtistName().isBlank()) {
            artist.setName(request.getArtistName().trim());
            isUpdated = true;
        }

        // ===== UPDATE AVATAR =====
        if (request.getAvatar() != null && !request.getAvatar().isEmpty()) {
            // Xóa avatar cũ nếu có
            if (artist.getAvatar() != null && !artist.getAvatar().isBlank()) {
                fileStorageService.deleteByUrl(artist.getAvatar());
            }
            String newAvatarUrl = fileStorageService.upload(request.getAvatar(), "artists");
            artist.setAvatar(newAvatarUrl);
            isUpdated = true;
        }

        if (!isUpdated) {
            throw new IllegalArgumentException("Không có dữ liệu nào để cập nhật");
        }
        artistRepository.save(artist);
        return ArtistMapper.toResponse(artist);
    }

    // ===== DELETE =====
    public void deleteArtistById(UUID artistId) {
        Artist artist = findArtistById(artistId);
        // xóa file avatar
        fileStorageService.deleteByUrl(artist.getAvatar());
        artistRepository.delete(artist);
    }

    // ===== GET BY NAME =====
    public ArtistResponse getArtistByName(String artistName) {
        Artist artist = artistRepository.findByName(artistName);
        if (artist == null) {
            throw new IllegalArgumentException("Không tìm thấy nghệ sĩ: " + artistName);
        }
        return ArtistMapper.toResponse(artist);
    }

    // ===== GET ALL =====
    public List<ArtistResponse> getAllArtists() {
        return ArtistMapper.toResponseList(artistRepository.findAll());
    }

    // ===== GET BY ID =====
    public Artist findArtistById(UUID id) {
        return artistRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Nghệ sĩ không tồn tại với id: " + id));
    }
}