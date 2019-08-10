package AndrewS.GraduationTJ.repository;

import AndrewS.GraduationTJ.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudDishRepository extends JpaRepository<Dish, Integer> {
}
