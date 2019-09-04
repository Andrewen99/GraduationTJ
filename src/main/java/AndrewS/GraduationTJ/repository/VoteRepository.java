package AndrewS.GraduationTJ.repository;

import AndrewS.GraduationTJ.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class VoteRepository {

    @Autowired
    private CrudVoteRepository voteRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    public Vote get(int id) {
        return voteRepository.findById(id).orElse(null);
    }

    public boolean delete(int id) {
        return voteRepository.delete(id) != 0;
    }

    public Vote save(Vote vote, int userId, int resId) {
        vote.setVoter(userRepository.get(userId));
        vote.setElected(restaurantRepository.get(resId));
        return voteRepository.save(vote);
    }

    public List<Vote> getInDate(LocalDate date) {
        return voteRepository.getInDate(date);
    }

    public Vote getInDateByUser(LocalDate date, int userId) {
        return voteRepository.getInDateByUser(date, userId);
    }


}
