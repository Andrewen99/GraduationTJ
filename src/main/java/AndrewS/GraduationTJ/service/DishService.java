package AndrewS.GraduationTJ.service;

import AndrewS.GraduationTJ.model.Dish;
import AndrewS.GraduationTJ.model.Restaurant;
import AndrewS.GraduationTJ.repository.DishRepository;
import AndrewS.GraduationTJ.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static AndrewS.GraduationTJ.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    @Autowired
    private DishRepository repository;

    public Dish create(Dish dish, int resId) {
        Assert.notNull(dish,"dish must not be null");
        return repository.save(dish, resId);
    }

    public void update(Dish dish, int resId) throws NotFoundException {
        Assert.notNull(dish,"dish must not be null");
        checkNotFoundWithId(repository.save(dish, resId), dish.getId());
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Dish get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Dish> getInDateByRestaurant(LocalDate date, int resId) {
        return repository.getInDateByRestaurant(date, resId);
    }

    public List<Dish> getAll(int resId) {
        return repository.getAll(resId);
    }

    public List<Dish> getAll() {return repository.getAll();}

}
