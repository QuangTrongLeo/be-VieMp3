package be_viemp3.viemp3.repository.finance;

import be_viemp3.viemp3.entity.Order;
import be_viemp3.viemp3.entity.User;
import be_viemp3.viemp3.enums.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findByVnpTxnRef(String vnpTxnRef);
    List<Order> findAllByOrderByOrderDateDesc();
    List<Order> findAllByUserOrderByOrderDateDesc(User user);
    List<Order> findByStatusAndExpiryDateBefore(OrderStatus status, LocalDateTime dateTime);
    boolean existsByUserAndStatusAndExpiryDateAfter(User user, OrderStatus status, LocalDateTime dateTime);
    @Modifying
    @Transactional
    void deleteByIdAndStatus(String id, OrderStatus status);
}
