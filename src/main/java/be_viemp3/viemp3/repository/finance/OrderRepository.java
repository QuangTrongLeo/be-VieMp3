package be_viemp3.viemp3.repository.finance;

import be_viemp3.viemp3.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findByVnpTxnRef(String vnpTxnRef);
}
