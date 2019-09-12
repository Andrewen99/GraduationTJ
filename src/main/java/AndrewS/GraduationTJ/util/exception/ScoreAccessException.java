package AndrewS.GraduationTJ.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "The score is available only after 11:00 for users that have voted")  // 403
public class ScoreAccessException extends RuntimeException {

    public ScoreAccessException(String message) {
        super(message);
    }
}
