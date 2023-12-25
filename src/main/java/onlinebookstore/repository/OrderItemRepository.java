package onlinebookstore.repository;

import java.util.Optional;
import onlinebookstore.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByIdAndOrder_Id(Long orderId, Long itemId);
}
