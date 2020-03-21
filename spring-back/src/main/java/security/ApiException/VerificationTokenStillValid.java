package security.ApiException;

public class VerificationTokenStillValid extends RuntimeException {
    public VerificationTokenStillValid(String s) {
        super(s);
    }
}
