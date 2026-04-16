package be_viemp3.viemp3.repository.finance;

import be_viemp3.viemp3.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {
    List<Voucher> findAllByActiveTrueAndQuantityGreaterThanAndEndDateAfter(Integer quantity, LocalDateTime now);
}