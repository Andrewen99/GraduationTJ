package AndrewS.GraduationTJ.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User voter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "res_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant elected;


    @Column(name = "date", nullable = false, columnDefinition =  "timestamp default now()")
    @NotNull
    private LocalDate date;


    public Vote(Integer id, User voter, Restaurant elected, LocalDate date) {
        super(id);
        this.voter = voter;
        this.elected = elected;
        this.date = date;
    }

    public Vote(Integer id) {
        super(id);
    }

    public Vote() {
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    public void setElected(Restaurant elected) {
        this.elected = elected;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
