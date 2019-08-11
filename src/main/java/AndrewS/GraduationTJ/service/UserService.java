package AndrewS.GraduationTJ.service;

import AndrewS.GraduationTJ.model.User;
import AndrewS.GraduationTJ.repository.UserRepository;
import AndrewS.GraduationTJ.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static AndrewS.GraduationTJ.util.ValidationUtil .checkNotFound;
import static AndrewS.GraduationTJ.util.ValidationUtil .checkNotFoundWithId;

//     Class is not finished and will work after data-jpa

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    public void update(User user) throws NotFoundException {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(user), user.getId());
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id),id);
    }

    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email = " + email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

}
