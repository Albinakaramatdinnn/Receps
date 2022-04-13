package MUIT.Recept.repositories;

import MUIT.Recept.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface FoodRepository extends JpaRepository<Food, Long> {
    Food findByIdAndAmountGreaterThan(Long id,int amount);

    Food findByName(String name);

    List<Food> findByDeletedAtNull();

    Food findFoodByDeletedAtNullAndId(Long id);

    Food findFoodByNameAndDeletedAtNull(String name);
}
