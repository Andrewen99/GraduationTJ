package AndrewS.GraduationTJ.repository;

//Waiting for data-jpa

import AndrewS.GraduationTJ.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    private static final Sort SORT_NAME_EMAIL = new Sort(Sort.Direction.ASC, "name", "email");

    @Autowired
    private CrudUserRepository repository;

    public User save(User user) {return repository.save(user);}

    public boolean delete(int id) {return repository.delete(id) != 0;}

    public User get(int id) {return repository.findById(id).orElse(null);}

    public User getByEmail(String email) {return repository.getByEmail(email);}

    public List<User> getAll() {return repository.findAll(SORT_NAME_EMAIL);}

}
