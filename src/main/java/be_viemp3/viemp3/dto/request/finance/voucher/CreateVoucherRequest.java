package be_viemp3.viemp3.dto.request.finance.voucher;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
public class CreateVoucherRequest {
    private Integer quantity;
    private Double discountPercentage;
    private Double maxDiscountAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
