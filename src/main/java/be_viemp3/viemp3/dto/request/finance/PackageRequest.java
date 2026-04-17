package be_viemp3.viemp3.dto.request.finance;

import be_viemp3.viemp3.enums.DurationType;
import be_viemp3.viemp3.enums.PackageType;
import lombok.Data;

@Data
public class PackageRequest {
    private PackageType type;
    private DurationType duration;
    private Double basePrice;
    private Double discountPercent;
}
