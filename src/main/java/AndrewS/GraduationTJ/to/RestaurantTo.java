package AndrewS.GraduationTJ.to;

import AndrewS.GraduationTJ.model.Dish;
import AndrewS.GraduationTJ.model.Restaurant;

import java.util.List;
import java.util.Objects;

public class RestaurantTo {

    private Integer id;

    private String name;

    private int countOfVotes;

    private List<DishTo> dishes;


    public RestaurantTo(Integer id, String name, Integer countOfVotes) {
        this.id = id;
        this.name = name;
        this.countOfVotes = countOfVotes;
    }

    public RestaurantTo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public RestaurantTo() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountOfVotes() {
        return countOfVotes;
    }

    public void setCountOfVotes(int countOfVotes) {
        this.countOfVotes = countOfVotes;
    }

    public List<DishTo> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishTo> dishes) {
        this.dishes = dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo that = (RestaurantTo) o;
        return name.equals(that.name) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id = " + id +
                ", name = " + name +
                ", VoteCount = " + countOfVotes
                + "}";
    }
}
