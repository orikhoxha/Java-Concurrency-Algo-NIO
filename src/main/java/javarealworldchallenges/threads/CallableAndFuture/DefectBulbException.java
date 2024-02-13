package javarealworldchallenges.threads.CallableAndFuture;

import java.util.concurrent.ExecutionException;

public class DefectBulbException extends ExecutionException {

    private static final long serialVersionUID = 1L;

    public DefectBulbException() {
        super();
    }

    public DefectBulbException(String msg) {
        super(msg);
    }

    public DefectBulbException(Throwable cause) {
        super(cause);
    }

    public DefectBulbException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
