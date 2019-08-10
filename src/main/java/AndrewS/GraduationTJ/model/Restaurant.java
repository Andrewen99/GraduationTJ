package AndrewS.GraduationTJ.model;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "elected")
    private Set<Vote> votes;

    public Restaurant(Integer id, String name, List<Dish> dishes) {
        super(id, name);
        this.dishes = dishes;
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant() {

    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public Set<Vote> getVotes() {
        return votes;
    }
}
