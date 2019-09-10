package AndrewS.GraduationTJ.service;

import AndrewS.GraduationTJ.model.Dish;
import AndrewS.GraduationTJ.model.Restaurant;
import AndrewS.GraduationTJ.repository.DishRepository;
import AndrewS.GraduationTJ.to.DishTo;
import AndrewS.GraduationTJ.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static AndrewS.GraduationTJ.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    @Autowired
    private DishRepository repository;

    @CacheEvict(value = {"restaurants", "restaurantsTo"}, allEntries = true)
    public Dish create(Dish dish, int resId) {
        Assert.notNull(dish,"dish must not be null");
        return repository.save(dish, resId);
    }

    @CacheEvict(value = {"restaurants", "restaurantsTo"}, allEntries = true)
    public void update(Dish dish, int resId) throws NotFoundException {
        Assert.notNull(dish,"dish must not be null");
        checkNotFoundWithId(repository.save(dish, resId), dish.getId());
    }

    @CacheEvict(value = {"restaurants", "restaurantsTo"}, allEntries = true)
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Dish get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<DishTo> getInDateByRestaurant(LocalDate date, int resId) {
        return repository.getInDateByRestaurant(date, resId)
                .stream()
                .map(dish -> new DishTo(dish.getId(), dish.getName(), dish.getPrice()))
                .collect(Collectors.toList());
    }

    public List<Dish> getAll(int resId) {
        return repository.getAll(resId);
    }

    public List<Dish> getAll() {return repository.getAll();}

}
