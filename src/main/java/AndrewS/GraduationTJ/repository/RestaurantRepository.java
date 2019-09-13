package AndrewS.GraduationTJ.repository;


import AndrewS.GraduationTJ.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantRepository {

    @Autowired
    private CrudRestaurantRepository repository;

    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    public Restaurant get(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    public Restaurant getWithDishes(int id) {
        return repository.getWithDishes(id);
    }

    public Restaurant getWithVotes(int id) {
        return repository.getWithVotes(id);
    }

    public Restaurant getWithDishesAndVotes(int id) {
        return  repository.getWithDishesAndVotes(id);
    }

    public List<Restaurant> getAllWithVotes() {
        return repository.getAllWithVotes();
    }

    public List<Restaurant> getAllWithDishes() {return repository.getAllWithDishes();}


}
