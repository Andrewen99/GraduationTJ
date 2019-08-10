package AndrewS.GraduationTJ.repository;

import AndrewS.GraduationTJ.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudVoteRepository extends JpaRepository<Vote,Integer > {
}
