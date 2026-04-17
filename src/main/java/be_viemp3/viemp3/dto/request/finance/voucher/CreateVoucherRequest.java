package be_viemp3.viemp3.dto.request.finance.voucher;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
public class CreateVoucherRequest {
    private Integer quantity;
    private Double discountPercentage;
    private Double maxDiscountAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate endDate;
}
