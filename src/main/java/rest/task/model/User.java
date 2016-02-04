package rest.task.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonDeserialize
public class User implements Serializable {

    @NotNull
    private UserInfoHolder userInfoHolder;

    @JsonCreator
    public User(@JsonProperty("user") UserInfoHolder userInfoHolder) {
        if (userInfoHolder == null) {
            this.userInfoHolder = NULL_HOLDER;
        }
        else {
            this.userInfoHolder = userInfoHolder;
        }
    }

    public User(String userName, String email) {
        this(new UserInfoHolder(userName, email));
    }

    public String getUserName() {
        return userInfoHolder.getUserName();
    }

    public String getEmail() {
        return userInfoHolder.getEmail();
    }

    private static class UserInfoHolder implements Serializable {
        private String userName;
        private String email;

        @JsonCreator
        public UserInfoHolder(@JsonProperty("username") String userName, @JsonProperty("email") String email) {
            this.userName = userName;
            this.email = email;
        }

        public String getUserName() {
            return userName;
        }

        public String getEmail() {
            return email;
        }
    }

    private static final UserInfoHolder NULL_HOLDER = new UserInfoHolder(null, null);
}
