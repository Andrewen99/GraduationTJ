package AndrewS.GraduationTJ.service;

import AndrewS.GraduationTJ.model.User;
import AndrewS.GraduationTJ.repository.UserRepository;
import AndrewS.GraduationTJ.util.exception.NotFoundException;

import java.util.List;

//     Class is not finished and will work after data-jpa

public class UserService {

    private UserRepository userRepository;

    public User create() {
        return null;
    }

    public void delete() throws NotFoundException {

    }

    public User get(int id) throws NotFoundException {
        return null;
    }

    public User getByEmail(String email) throws NotFoundException {
        return null;
    }

    public List<User> getAll() {
        return null;
    }

    public void update(User user) throws NotFoundException {

    }

}
