package be_viemp3.viemp3.dto.request.finance.voucher;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UpdateVoucherRequest {
    private Integer quantity;
    private Double discountPercentage;
    private Double maxDiscountAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
}
