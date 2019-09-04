package AndrewS.GraduationTJ.repository;

import AndrewS.GraduationTJ.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote,Integer > {
    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT m from Vote m WHERE  m.date=:date")
    List<Vote> getInDate(@Param("date") LocalDate date);

    @Query("SELECT m from Vote m WHERE  m.date=:date AND m.voter.id=:userId")
    Vote getInDateByUser(@Param("date") LocalDate date,@Param("userId") int userId);

    @Query("SELECT m from Vote m WHERE  m.voter.id=:userId")
    List<Vote> getAllByUser(@Param("userId") int userId);
}
