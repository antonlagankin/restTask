package rest.task.client;

import rest.task.exceptions.UserNotFoundException;
import rest.task.model.Product;
import rest.task.model.Purchases;
import rest.task.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

public class RestClientImpl implements RestClient {

    private static final String RECENT_PURCHASES_URL_TEMPLATE = "%s%s/%s?limit=%s";
    private static final String PURCHASES_BY_PRIDUCTID_URL_TEMPLATE = "%s%s/%s";
    private static final String PRODUCTS_BY_ID_URL_TEMPLATE = "%s%s/%s";
    private static final String USERS_BY_NAME_URL_TEMPLATE = "%s%s/%s";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ClientConfiguration clientConfiguration;

    @Override
    @Cacheable(value="popularUploads", key="#userName")
    public Purchases getPopularPurchases(String userName) {
        String recentPurchasesUrl = String.format(RECENT_PURCHASES_URL_TEMPLATE, clientConfiguration.getApiEndpoint(), clientConfiguration.getPurchasesUrl(), userName, clientConfiguration.getPurchasesLimit());
        return restTemplate.getForObject(recentPurchasesUrl, Purchases.class);
    }

    @Override
    @Cacheable(value="purchases", key="#productId")
    public Purchases getPurchases(int productId) {
        String purchasesByProductId = String.format(PURCHASES_BY_PRIDUCTID_URL_TEMPLATE, clientConfiguration.getApiEndpoint(), clientConfiguration.getPurchasesByProductId(), productId);
        return restTemplate.getForObject(purchasesByProductId, Purchases.class);
    }

    @Override
    @Cacheable(value="products", key="#productId")
    public Product getProduct(int productId) {
        String productByIdUrl = String.format(PRODUCTS_BY_ID_URL_TEMPLATE, clientConfiguration.getApiEndpoint(), clientConfiguration.getProducts(), productId);
        return restTemplate.getForObject(productByIdUrl, Product.class);
    }

    @Override
    @Cacheable(value="users", key="#name")
    public User findUserByName(String name) throws UserNotFoundException {
        String userByNameUrl = String.format(USERS_BY_NAME_URL_TEMPLATE, clientConfiguration.getApiEndpoint(), clientConfiguration.getUsersUrl(), name);
        User user = restTemplate.getForObject(userByNameUrl, User.class);
        if (user == null || StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getEmail())) {
            throw new UserNotFoundException(name);
        }
        return user;
    }

}
