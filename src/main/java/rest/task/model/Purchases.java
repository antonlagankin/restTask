package rest.task.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@JsonRootName("purchases")
public class Purchases implements Serializable {

    private List<Purchase> purchases;

    @JsonCreator
    public Purchases(@JsonProperty("purchases") List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public List<Purchase> getPurchases() {
        return Collections.unmodifiableList(purchases);
    }
}
