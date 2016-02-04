package rest.task.client;

import rest.task.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorsController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException userNotFoundException) {
        String msg = "User with username of '" + userNotFoundException.getRequestedUserName() + "' was not found";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(msg, HttpStatus.OK);
        return responseEntity;
    }

}
