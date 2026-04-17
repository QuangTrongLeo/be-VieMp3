package be_viemp3.viemp3.dto.response.finance;

import be_viemp3.viemp3.enums.DurationType;
import be_viemp3.viemp3.enums.PackageType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PackageResponse {
    private String id;
    private PackageType packageType;
    private DurationType duration;
    private Double basePrice;
    private Double discountPercent;
    private Double finalPrice;
    private LocalDateTime createdAt;
}
