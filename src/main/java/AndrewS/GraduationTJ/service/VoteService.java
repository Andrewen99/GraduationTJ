package AndrewS.GraduationTJ.service;

import AndrewS.GraduationTJ.model.Vote;
import AndrewS.GraduationTJ.repository.VoteRepository;
import AndrewS.GraduationTJ.util.exception.NotFoundException;
import AndrewS.GraduationTJ.util.exception.VoteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @CacheEvict(value = "votes",allEntries = true)
    public Vote create( int userId, int resId) throws VoteException {
        LocalDateTime now = LocalDateTime.now();
        Vote vote = new Vote();
        Vote voteFromUser = voteRepository.getInDateByUser(now.toLocalDate(),userId);

        if(voteFromUser != null) {
            throw new VoteException("User has already voted today");
        }
        vote.setDate(now.toLocalDate());
        return voteRepository.save(vote, userId, resId);
    }

    @CacheEvict(value = "votes",allEntries = true)
    public void update(int voteId, int userId, int resId) throws Exception {
        Vote vote = get(voteId);
        Assert.notNull(vote,"vote must not be null");
        LocalDateTime now = LocalDateTime.now();
        LocalDate voteDate = vote.getDate();


        if (    vote.getVoter().getId() == userId &&
                now.toLocalDate().equals(voteDate) &&
                now.toLocalTime().isBefore(LocalTime.of(11,0))
            ) {
            checkNotFoundWithId(voteRepository.save(vote, userId, resId), vote.getId());
        } else {
            throw new VoteException("You can't vote again after 11 A.M.");
        }
    }

    @CacheEvict(value = "votes",allEntries = true)
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(voteRepository.delete(id),id);
    }

    public Vote get(int id) throws NotFoundException {
        return checkNotFoundWithId(voteRepository.get(id),id);
    }

    @Cacheable("votes")
    public List<Vote> getInDate(LocalDate date) {
        return voteRepository.getInDate(date);
    }

    @Cacheable("votes")
    public Vote getInDateByUser(LocalDate date, int userId) {
        return voteRepository.getInDateByUser(date, userId);
    }


    @CacheEvict(value = "votes",allEntries = true)
    public void cacheEvict(){}

}
