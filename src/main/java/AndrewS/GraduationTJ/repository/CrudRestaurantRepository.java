package AndrewS.GraduationTJ.repository;

import AndrewS.GraduationTJ.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
