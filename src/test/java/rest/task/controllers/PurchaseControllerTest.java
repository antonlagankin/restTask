package rest.task.controllers;

import rest.task.client.RestClient;
import rest.task.exceptions.UserNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rest.task.model.*;
import rest.task.spring.PurchaseControllerTestConfig;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PurchaseControllerTestConfig.class)
public class PurchaseControllerTest {

    private static final String REQUESTED_USER_NAME = "username";
    private static final String SECOND_BUYER_NAME = "user2";

    @Autowired
    private RestClient mockRestClient;

    @InjectMocks
    @Autowired
    private PurchasesController purchasesController;

    @Mock
    private User mockUser;

    @Mock
    private Purchases purchases;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() throws UserNotFoundException, ExecutionException, InterruptedException {
        when(mockRestClient.findUserByName(same(REQUESTED_USER_NAME))).thenReturn(mockUser);
        when(mockUser.getUserName()).thenReturn(REQUESTED_USER_NAME);
        when(mockRestClient.getPopularPurchases(same(REQUESTED_USER_NAME))).thenReturn(purchases);

        Purchase purchase1 = buildMockPurchase(1);
        Purchase purchase2 = buildMockPurchase(2);

        when(purchases.getPurchases()).thenReturn(Arrays.asList(purchase1, purchase2));

        Product product1 = build(purchase1.getProductId(), REQUESTED_USER_NAME);
        Product product2 = build(purchase2.getProductId(), REQUESTED_USER_NAME, REQUESTED_USER_NAME, SECOND_BUYER_NAME);

        List<ProductBuyersHolder> productBuyersHolderList = purchasesController.getRecentPurchases(REQUESTED_USER_NAME);

        assertEquals(2, productBuyersHolderList.size());
        assertSame(product2, productBuyersHolderList.get(0).getProduct());
        assertSame(product1, productBuyersHolderList.get(1).getProduct());

        assertEquals(2, productBuyersHolderList.get(0).getBuyersName().size());
        assertEquals(1, productBuyersHolderList.get(1).getBuyersName().size());

        assertEquals(3, productBuyersHolderList.get(0).getRunk());
        assertEquals(1, productBuyersHolderList.get(1).getRunk());

        Stream.of(REQUESTED_USER_NAME, SECOND_BUYER_NAME).forEach(userName -> assertTrue(productBuyersHolderList.get(0).getBuyersName().contains(userName)));
        assertTrue(productBuyersHolderList.get(1).getBuyersName().contains(REQUESTED_USER_NAME));
    }

    private Purchase buildMockPurchase(int productId) {
        Purchase purchase = Mockito.mock(Purchase.class);
        when(purchase.getProductId()).thenReturn(productId);

        return purchase;
    }

    private Purchases buildMockPurchases() {
        Purchases purchases = Mockito.mock(Purchases.class);

        return purchases;
    }

    private Product buildProduct(int productId) {
        Product product = Mockito.mock(Product.class);
        when(product.getId()).thenReturn(productId);
        return product;
    }

    private Product build(int productId, String ... userNames) {
        Purchases purchases = buildMockPurchases();
        Product product = buildProduct(productId);

        when(mockRestClient.getPurchases(productId)).thenReturn(purchases);
        when(mockRestClient.getProduct(productId)).thenReturn(product);

        List<Purchase> purchaseList = Stream.of(userNames).map(userName -> {
            Purchase purchase = Mockito.mock(Purchase.class);
            when(purchase.getUserName()).thenReturn(userName);
            return purchase;
        }).collect(Collectors.toList());

        when(purchases.getPurchases()).thenReturn(purchaseList);
        return product;
    }

}
