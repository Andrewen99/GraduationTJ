package AndrewS.GraduationTJ.service;

import AndrewS.GraduationTJ.model.Restaurant;
import AndrewS.GraduationTJ.repository.RestaurantRepository;
import AndrewS.GraduationTJ.repository.VoteRepository;
import AndrewS.GraduationTJ.to.RestaurantTo;
import AndrewS.GraduationTJ.util.restaurant.RestaurantUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import AndrewS.GraduationTJ.util.exception.NotFoundException;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static AndrewS.GraduationTJ.util.restaurant.RestaurantUtil.getWithFilteredInDateDishes;


import static AndrewS.GraduationTJ.util.ValidationUtil .checkNotFoundWithId;
@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private DishService dishService;


    @CacheEvict(value = {"restaurants", "restaurantsTo"}, allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant,"restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = {"restaurants", "restaurantsTo"}, allEntries = true)
    public void update(Restaurant restaurant) throws NotFoundException {
        Assert.notNull(restaurant,"restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }

    @CacheEvict(value = {"restaurants", "restaurantsTo"}, allEntries = true)
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(restaurantRepository.delete(id), id);
    }

    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    public List<RestaurantTo> getAllTo() {
        return RestaurantUtil.getAllWithCountOfVotes(restaurantRepository.getAllWithVotes());
    }

    public Restaurant getWithDishes(int id) {
        return restaurantRepository.getWithDishes(id);
    }

    @Cacheable("restaurantsTo")
    public RestaurantTo getInDateWithDishes(LocalDate date, int id) {
        Restaurant restaurant = getWithDishes(id);
        RestaurantTo restaurantTo = getWithFilteredInDateDishes(restaurant, date);
        return restaurantTo;
    }

    public RestaurantTo getWithVotes(int id, LocalDate date) {
        Restaurant restaurant = restaurantRepository.getWithVotes(id);
        return RestaurantUtil.getWithFilteredCountOfVotes(restaurant,date);
    }

    public List<RestaurantTo> getAllWithVotes(LocalDate date) {
        List<Restaurant> restaurants = restaurantRepository.getAllWithVotes();
        return RestaurantUtil.getAllWithFilteredCountOfVotes(restaurants, date);
    }

    @Cacheable("restaurantsTo")
    public RestaurantTo getInDateWithDishesAndVotes(int id, LocalDate date) {
        Restaurant restaurant = restaurantRepository.getWithDishesAndVotes(id);
        return RestaurantUtil.getWithFilteredDishesAndCountOfVotes(restaurant, date);
    }

    @Cacheable("score")
    public List<RestaurantTo> getRestaurantsWithScore(int userId) {
        LocalDateTime now = LocalDateTime.now();
        if(voteRepository.getInDateByUser(now.toLocalDate(),userId)!=null && now.toLocalTime().isAfter(LocalTime.of(11,0))){
            return getAllWithVotes(now.toLocalDate());
        }
        else {
            return null;
        }
    }

    @CacheEvict(value = "score",allEntries = true)
    public void cacheScoreEvict(){}

    @CacheEvict(value = "restaurants",allEntries = true)
    public void cacheRestaurantsEvict(){}

    @CacheEvict(value = "restaurantsTo",allEntries = true)
    public void cacheRestaurantsToEvict(){}



}
