package security.ApiException;

public class VerificationTokenNotFoundException extends RuntimeException {

    public VerificationTokenNotFoundException(String s) {
        super(s);
    }
}
