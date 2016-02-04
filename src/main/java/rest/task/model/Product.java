package rest.task.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Product implements Serializable {

    public final ProductHolder productHolder;

    @JsonCreator
    public Product(@JsonProperty("product") ProductHolder productHolder) {
        this.productHolder = productHolder;
    }

    public int getId() {
        return productHolder.getId();
    }

    public String getFace() {
        return productHolder.getFace();
    }

    public int getPrice() {
        return productHolder.getPrice();
    }

    public int getSize() {
        return productHolder.getSize();
    }

    @JsonIgnoreType
    public static class ProductHolder implements Serializable {
        private int id;
        private String face;
        private int size;
        private int price;

        @JsonCreator
        public ProductHolder(@JsonProperty("id") int id,
                             @JsonProperty("face") String face,
                             @JsonProperty("size") int size,
                             @JsonProperty("price") int price) {
            this.id = id;
            this.face = face;
            this.size = size;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public String getFace() {
            return face;
        }

        public int getSize() {
            return size;
        }

        public int getPrice() {
            return price;
        }
    }

}
