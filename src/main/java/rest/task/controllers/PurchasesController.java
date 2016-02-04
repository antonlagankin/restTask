package rest.task.controllers;

import rest.task.client.RestClient;
import rest.task.exceptions.UserNotFoundException;
import rest.task.model.Product;
import rest.task.model.ProductBuyersHolder;
import rest.task.model.Purchases;
import rest.task.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@RestController
public class PurchasesController {

    @Autowired
    private RestClient client;

    @RequestMapping("/api/recent_purchases/{username}")
    public List<ProductBuyersHolder> getRecentPurchases(@PathVariable(value = "username") String userName) throws ExecutionException, InterruptedException, UserNotFoundException {
        Purchases popularPurchases = getPurchases(userName);
        return getProductsWithBuyersSortedByPopularity(popularPurchases);
    }

    private Purchases getPurchases(String userName) throws UserNotFoundException {
        User user = client.findUserByName(userName);
        return client.getPopularPurchases(user.getUserName());
    }

    private List<ProductBuyersHolder> getProductsWithBuyersSortedByPopularity(Purchases popularPurchases) {
        return popularPurchases.getPurchases()
                .parallelStream()
                .map(popularPurchase -> {
                    Purchases purchases = client.getPurchases(popularPurchase.getProductId());
                    Product product = client.getProduct(popularPurchase.getProductId());
                    List<String> userNames = purchases.getPurchases().stream().map(t -> t.getUserName()).collect(Collectors.toList());
                    return new ProductBuyersHolder(product, userNames, userNames.size());
                })
                .sorted(Comparator.comparing((ProductBuyersHolder productBuyersHolder) -> productBuyersHolder.getRunk()).reversed())
                .collect(Collectors.toList());
    }
}
