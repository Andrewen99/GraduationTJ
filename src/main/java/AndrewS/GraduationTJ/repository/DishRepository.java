package AndrewS.GraduationTJ.repository;


import AndrewS.GraduationTJ.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DishRepository {

    @Autowired
    CrudDishRepository dishRepository;

    @Autowired
    CrudRestaurantRepository restaurantRepository;

    public Dish get(int id) {
        return dishRepository.findById(id).orElse(null);
    }

    public Dish save(Dish dish, int resId) {
        if (!dish.isNew() && get(dish.getId()) == null) {
            return null;
        }
        if (restaurantRepository.findById(resId).orElse(null) == null) {
            return null;
        }
        dish.setRestaurant(restaurantRepository.getOne(resId));
        return dishRepository.save(dish);
    }

    public boolean delete(int id) {
        return dishRepository.delete(id) != 0;
    }

    public List<Dish> getInDateByRestaurant(LocalDate date, int resId) {
        return dishRepository.getInDateByRestaurant(date, resId);
    }


    public List<Dish> getAll(int resId) {
        return dishRepository.getAll(resId);
    }

    public List<Dish> getAll() {
        return dishRepository.findAll();
    }





}
