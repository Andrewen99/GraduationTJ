package AndrewS.GraduationTJ.web.user;

import AndrewS.GraduationTJ.model.Restaurant;
import AndrewS.GraduationTJ.model.Vote;
import AndrewS.GraduationTJ.service.RestaurantService;
import AndrewS.GraduationTJ.service.VoteService;
import AndrewS.GraduationTJ.to.RestaurantTo;
import AndrewS.GraduationTJ.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping(value = UserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REST_URL = "/rest/user/restaurants";

    @Autowired
    VoteService voteService;

    @Autowired
    RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("{User} getAll");
        return restaurantService.getAllWithDishes();
    }

    @GetMapping("/{id}")
    public RestaurantTo getRestaurant(@PathVariable int id) {
        log.info("{User} getRestaurant");
        return restaurantService.getInDateWithDishes(LocalDate.now(), id);
    }

    @GetMapping("/score")
    public List<RestaurantTo> getRestaurantsWithScore() {
        log.info("{User} getRestaurantsWithScore");
        return restaurantService.getRestaurantsWithScore(SecurityUtil.authUserId());
    }

    @PostMapping("/voting")
    public Vote createVote(@RequestParam int resId) throws Exception {
        log.info("{User} create Vote for Restaurant with id: " + resId);
        return voteService.create(SecurityUtil.authUserId(), resId);
    }

    @PutMapping("/voting")
    public void updateVote(@RequestParam int resId, @RequestParam int voteId) throws Exception{
        log.info("{User} update Vote(id = {}) for Restaurant with id: {}" + voteId, resId);
        voteService.update(voteId, SecurityUtil.authUserId(),resId);
    }


}
