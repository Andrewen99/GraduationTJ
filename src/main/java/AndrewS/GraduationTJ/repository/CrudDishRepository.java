package AndrewS.GraduationTJ.repository;

import AndrewS.GraduationTJ.model.Dish;
import AndrewS.GraduationTJ.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;



@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    Dish save(Dish dish);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:resId")
    List<Dish> getAll(@Param("resId") int resId);


    @Query("SELECT d from Dish d WHERE  d.date=:date AND d.restaurant.id=:resId")
    List<Dish> getInDateByRestaurant(@Param("date") LocalDate date, @Param("resId") int resId);


}
