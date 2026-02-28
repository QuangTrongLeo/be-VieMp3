package be_viemp3.viemp3.dto.request.music.genre;

import be_viemp3.viemp3.enums.GenreEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateGenreRequest {
    @NotNull(message = "Tên genre không được để trống")
    private GenreEnum name;
}