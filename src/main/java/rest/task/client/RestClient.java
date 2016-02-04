package rest.task.client;

import rest.task.exceptions.UserNotFoundException;
import rest.task.model.Product;
import rest.task.model.Purchases;
import rest.task.model.User;

public interface RestClient {
    Purchases getPopularPurchases(String userName);
    Purchases getPurchases(int productId);
    Product getProduct(int productId);
    User findUserByName(String name) throws UserNotFoundException;
}
