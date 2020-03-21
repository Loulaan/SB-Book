package exception.ApiException;

public class RoleNotFoundException extends ApiException {

    public RoleNotFoundException() {
        this.exceptionPropertyKey = "errors.roles.not.found";
    }
}
