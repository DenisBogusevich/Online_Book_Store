package onlinebookstore.repository;

import java.util.Optional;
import java.util.Set;
import onlinebookstore.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "orderItems")
    Set<Order> findByUserId(Long id);

    @EntityGraph(attributePaths = "orderItems")
    Optional<Order> findByIdAndId(Long userId, Long id);
}
