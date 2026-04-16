package be_viemp3.viemp3.service.finance;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.dto.request.finance.voucher.CreateVoucherRequest;
import be_viemp3.viemp3.dto.request.finance.voucher.UpdateVoucherRequest;
import be_viemp3.viemp3.dto.response.finance.VoucherResponse;
import be_viemp3.viemp3.entity.Voucher;
import be_viemp3.viemp3.mapper.finance.VoucherMapper;
import be_viemp3.viemp3.repository.finance.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final EntityQueryService entityService;

    // 1. Lấy tất cả Voucher
    public List<VoucherResponse> getAllVouchers() {
        return VoucherMapper.toResponseList(voucherRepository.findAll());
    }

    // 2. Lấy danh sách Voucher khả dụng
    public List<VoucherResponse> getAvailableVouchers() {
        LocalDateTime now = LocalDateTime.now();
        List<Voucher> vouchers = voucherRepository.findAllByActiveTrueAndQuantityGreaterThanAndEndDateAfter(0, now);
        return VoucherMapper.toResponseList(vouchers);
    }

    // 3. Lấy chi tiết 1 Voucher
    public VoucherResponse getVoucherById(String id) {
        Voucher voucher = entityService.finVoucherById(id);
        return VoucherMapper.toResponse(voucher);
    }

    // 4. Tạo mới Voucher
    @Transactional
    public VoucherResponse createVoucher(CreateVoucherRequest request) {
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new RuntimeException("Ngày kết thúc không thể trước ngày bắt đầu");
        }

        Voucher voucher = new Voucher();
        voucher.setQuantity(request.getQuantity());
        voucher.setDiscountPercentage(request.getDiscountPercentage());
        voucher.setMaxDiscountAmount(request.getMaxDiscountAmount());
        voucher.setStartDate(request.getStartDate());
        voucher.setEndDate(request.getEndDate());
        voucher.setActive(true);

        return VoucherMapper.toResponse(voucherRepository.save(voucher));
    }

    // 5. Cập nhật Voucher
    @Transactional
    public VoucherResponse updateVoucher(String id, UpdateVoucherRequest request) {
        Voucher voucher = entityService.finVoucherById(id);

        voucher.setQuantity(request.getQuantity());
        voucher.setDiscountPercentage(request.getDiscountPercentage());
        voucher.setMaxDiscountAmount(request.getMaxDiscountAmount());
        voucher.setStartDate(request.getStartDate());
        voucher.setEndDate(request.getEndDate());
        voucher.setActive(request.isActive());

        return VoucherMapper.toResponse(voucherRepository.save(voucher));
    }

    // 6. Xóa Voucher (Nên dùng xóa mềm bằng cách set active = false)
    @Transactional
    public void deleteVoucher(String id) {
        Voucher voucher = entityService.finVoucherById(id);
        voucherRepository.delete(voucher);
    }

    // 7. Logic trừ số lượng Voucher khi sử dụng thanh toán
    @Transactional
    public void useVoucher(String id) {
        Voucher voucher = entityService.finVoucherById(id);

        if (voucher.getQuantity() <= 0) {
            throw new RuntimeException("Voucher đã hết lượt sử dụng");
        }

        voucher.setQuantity(voucher.getQuantity() - 1);
        voucherRepository.save(voucher);
    }
}