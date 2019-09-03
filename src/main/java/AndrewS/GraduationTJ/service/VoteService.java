package AndrewS.GraduationTJ.service;

import AndrewS.GraduationTJ.model.Vote;
import AndrewS.GraduationTJ.repository.VoteRepository;
import AndrewS.GraduationTJ.util.exception.NotFoundException;
import AndrewS.GraduationTJ.util.exception.VoteExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static AndrewS.GraduationTJ.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public Vote create( int userId, int resId) throws VoteExpiredException {
        LocalDateTime now = LocalDateTime.now();
        Vote vote = new Vote();
        Vote voteFromUser = voteRepository.getInDateByUser(now.toLocalDate(),userId);

        if(voteFromUser != null) {
            throw new VoteExpiredException("User has already voted today");
        }
        vote.setDate(now.toLocalDate());
        return voteRepository.save(vote, userId, resId);
    }

    public void update(Vote vote, int userId, int resId) throws Exception {
        Assert.notNull(vote,"vote must not be null");
        LocalDateTime now = LocalDateTime.now();
        LocalDate voteDate = vote.getDate();

        if (    vote.getVoter().getId() == userId &&
                now.toLocalDate().equals(voteDate) &&
                now.toLocalTime().isBefore(LocalTime.of(11,0))
            ) {
            checkNotFoundWithId(voteRepository.save(vote, userId, resId), vote.getId());
        } else throw new VoteExpiredException("You can't vote after 11 o'clock");
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(voteRepository.delete(id),id);
    }

    public Vote get(int id) throws NotFoundException {
        return checkNotFoundWithId(voteRepository.get(id),id);
    }

    public List<Vote> getInDate(LocalDate date) {
        return voteRepository.getInDate(date);
    }

    public Vote getInDateByUser(LocalDate date, int userId) {
        return voteRepository.getInDateByUser(date, userId);
    }


}
