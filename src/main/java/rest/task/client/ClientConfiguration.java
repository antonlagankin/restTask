package rest.task.client;

import org.springframework.beans.factory.annotation.Value;

public class ClientConfiguration {
    @Value("${api.endpoint}")
    private String apiEndpoint;

    @Value("${api.purchases.url}")
    private String purchasesUrl;

    @Value("${api.purchases.by.productid}")
    private String purchasesByProductId;

    @Value("${api.products}")
    private String products;

    @Value("${purchases.limit}")
    private String purchasesLimit;

    @Value("${api.users.url}")
    private String usersUrl;

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public String getPurchasesUrl() {
        return purchasesUrl;
    }

    public String getPurchasesByProductId() {
        return purchasesByProductId;
    }

    public String getPurchasesLimit() {
        return purchasesLimit;
    }

    public String getProducts() {
        return products;
    }

    public String getUsersUrl() {
        return usersUrl;
    }
}
