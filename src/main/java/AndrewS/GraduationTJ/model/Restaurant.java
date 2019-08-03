package AndrewS.GraduationTJ.model;

import java.util.HashMap;

public class Restaurant extends AbstractNamedEntity {
    protected HashMap<String, Integer> meals;

    public Restaurant(Integer id, String name, HashMap<String, Integer> meals) {
        super(id, name);
        this.meals = meals;
    }
}
