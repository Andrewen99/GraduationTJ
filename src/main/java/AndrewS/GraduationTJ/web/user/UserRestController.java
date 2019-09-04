package AndrewS.GraduationTJ.web.user;

import AndrewS.GraduationTJ.model.Restaurant;
import AndrewS.GraduationTJ.model.Vote;
import AndrewS.GraduationTJ.service.RestaurantService;
import AndrewS.GraduationTJ.service.VoteService;
import AndrewS.GraduationTJ.to.RestaurantTo;
import AndrewS.GraduationTJ.web.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping(value = UserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    public static final String REST_URL = "/rest/user/restaurants";

    @Autowired
    VoteService voteService;

    @Autowired
    RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    public RestaurantTo getRestaurant(@PathVariable int id) {
        return restaurantService.getInDateWithDishes(LocalDate.now(), id);
    }

    @GetMapping("/score")
    public List<RestaurantTo> getRestaurantsWithScore() {
        return restaurantService.getRestaurantsWithScore(SecurityUtil.authUserId());
    }

    @PostMapping("/{id}")
    public Vote createVote(@PathVariable int id) throws Exception {
        return voteService.create(SecurityUtil.authUserId(), id);
    }

    @PutMapping("/{id}/{voteId}")
    public void updateVote(@PathVariable(name = "id") int resId,@PathVariable int voteId) throws Exception {
        voteService.update(voteId, SecurityUtil.authUserId(), resId );
    }


}
