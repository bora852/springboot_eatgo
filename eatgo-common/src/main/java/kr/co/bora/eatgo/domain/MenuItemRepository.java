package kr.co.bora.eatgo.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

//public interface MenuItemRepository extends JpaRepository<MenuItem, Long>{
public interface MenuItemRepository extends CrudRepository<MenuItem, Long>{
    List<MenuItem> findAllByRestaurantId(Long restaurantId);

    void deleteById(Long id);
}
