package be_viemp3.viemp3.service.music;

import be_viemp3.viemp3.dto.request.music.artist.CreateAristRequest;
import be_viemp3.viemp3.dto.request.music.artist.UpdateArtistAvatarRequest;
import be_viemp3.viemp3.dto.request.music.artist.UpdateArtistNameRequest;
import be_viemp3.viemp3.dto.response.music.ArtistResponse;
import be_viemp3.viemp3.entity.Artist;
import be_viemp3.viemp3.mapper.music.ArtistMapper;
import be_viemp3.viemp3.repository.music.ArtistRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Value("${file.upload-img-dir}")
    private String uploadImgDir;

    @Value("${server.domain-url}")
    private String domainUrl;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    // CREATE ARTIST
    public ArtistResponse createArtist(CreateAristRequest request) {
        MultipartFile file = request.getAvatar();
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        try {
            Path uploadPath = Paths.get(uploadImgDir);
            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath);

            Artist artist = new Artist();
            artist.setName(request.getName());
            artist.setAvatar(domainUrl + "/uploads/" + fileName);

            artistRepository.save(artist);
            return ArtistMapper.toResponse(artist);

        } catch (IOException e) {
            throw new RuntimeException("Lỗi upload file: " + e.getMessage(), e);
        }
    }

    // UPDATE ARTIST NAME
    public ArtistResponse updateArtistName(UpdateArtistNameRequest request) {
        Artist artist = findArtistById(request.getArtistId());

        artist.setName(request.getArtistName());
        artistRepository.save(artist);

        return ArtistMapper.toResponse(artist);
    }

    // UPDATE ARTIST AVATAR
    public ArtistResponse updateArtistAvatar(UpdateArtistAvatarRequest request) {
        Artist artist = findArtistById(request.getArtistId());

        MultipartFile file = request.getAvatar();
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File avatar không được để trống");
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        try {
            Path uploadPath = Paths.get(uploadImgDir);
            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath);

            // Nếu muốn, có thể xóa avatar cũ tại server
            String oldAvatarPath = artist.getAvatar();
            if (oldAvatarPath != null && oldAvatarPath.startsWith(domainUrl + "/uploads/")) {
                Path oldFile = Paths.get(uploadImgDir).resolve(oldAvatarPath.substring((domainUrl + "/uploads/").length()));
                Files.deleteIfExists(oldFile);
            }

            // Cập nhật avatar mới
            artist.setAvatar(domainUrl + "/uploads/" + fileName);
            artistRepository.save(artist);

            return ArtistMapper.toResponse(artist);

        } catch (IOException e) {
            throw new RuntimeException("Lỗi upload file: " + e.getMessage(), e);
        }
    }

    // DELETE ARTIST
    public void deleteArtistById(Long artistId) {
        try {
            artistRepository.deleteById(artistId);
        } catch (EmptyResultDataAccessException e) {
            // Không tìm thấy artist theo ID
            throw new RuntimeException("Không tìm thấy nghệ sĩ với ID: " + artistId);
        } catch (DataIntegrityViolationException e) {
            // Vi phạm ràng buộc quan hệ (còn album/bài hát tham chiếu)
            throw new RuntimeException("Không thể xóa nghệ sĩ vì còn dữ liệu liên quan (album, bài hát, ...)");
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa nghệ sĩ: " + e.getMessage(), e);
        }
    }

    // GET ARTIST BY NAME
    public ArtistResponse getArtistByName(String artistName) {
        Artist artist = artistRepository.findByName(artistName);
        if (artist == null) {
            throw new RuntimeException("Không tìm thấy nghệ sĩ có tên: " + artistName);
        }
        return ArtistMapper.toResponse(artist);
    }

    // GET ALL ARTIST
    public List<ArtistResponse> getAllArtists() {
        List<Artist> artists = artistRepository.findAll();
        if (artists.isEmpty()) {
            throw new RuntimeException("Không có nghệ sĩ nào trong hệ thống");
        }

        return ArtistMapper.toResponseList(artists);
    }

    // ===== FIND ARTIST BY ID =====
    private Artist findArtistById(Long artistId){
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Nghệ sĩ không tồn tại với id: " + artistId));
    }
}