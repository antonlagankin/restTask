package rest.task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.io.Serializable;
import java.util.*;

public class ProductBuyersHolder implements Serializable {

    private Product product;
    private Set<String> buyersName;
    private int runk;

    public ProductBuyersHolder(Product product, Collection<String> buyersName, int runk) {
        this.product = product;
        this.buyersName = new HashSet<>(buyersName);
        this.runk = runk;
    }

    @JsonUnwrapped
    public Product getProduct() {
        return product;
    }

    @JsonProperty("recent")
    public Set<String> getBuyersName() {
        return Collections.unmodifiableSet(buyersName);
    }

    @JsonIgnore
    public int getRunk() {
        return runk;
    }
}
