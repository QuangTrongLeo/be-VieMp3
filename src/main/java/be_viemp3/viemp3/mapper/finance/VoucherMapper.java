package be_viemp3.viemp3.mapper.finance;

import be_viemp3.viemp3.dto.response.finance.VoucherResponse;
import be_viemp3.viemp3.entity.Voucher;

import java.util.List;
import java.util.stream.Collectors;

public class VoucherMapper {

    public static VoucherResponse toResponse(Voucher voucher) {
        if (voucher == null) {
            return null;
        }

        return VoucherResponse.builder()
                .id(voucher.getId())
                .quantity(voucher.getQuantity())
                .discountPercentage(voucher.getDiscountPercentage())
                .maxDiscountAmount(voucher.getMaxDiscountAmount())
                .startDate(voucher.getStartDate())
                .endDate(voucher.getEndDate())
                .createdAt(voucher.getCreatedAt())
                .active(voucher.isActive())
                .build();
    }

    public static List<VoucherResponse> toResponseList(List<Voucher> vouchers) {
        if (vouchers == null) {
            return List.of();
        }

        return vouchers.stream()
                .map(VoucherMapper::toResponse)
                .collect(Collectors.toList());
    }
}
