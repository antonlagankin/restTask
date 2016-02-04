package rest.task.exceptions;

public class UserNotFoundException extends Exception {

    private String requestedUserName;

    public UserNotFoundException(String requestedUserName) {
        this.requestedUserName = requestedUserName;
    }

    public String getRequestedUserName() {
        return requestedUserName;
    }
}
