package AndrewS.GraduationTJ.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Score of votes is not available")
public class VoteException extends RuntimeException {
    public VoteException(String message) {
        super(message);
    }
}
