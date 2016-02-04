package rest.task.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Purchase implements Serializable {

    private int id;
    private String userName;
    private int productId;
    private String date;

    @JsonCreator
    public Purchase(@JsonProperty("id") int id,
                    @JsonProperty("username") String userName,
                    @JsonProperty("productId") int productId,
                    @JsonProperty("date") String date) {
        this.id = id;
        this.userName = userName;
        this.productId = productId;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public int getProductId() {
        return productId;
    }

    public String getDate() {
        return date;
    }
}
