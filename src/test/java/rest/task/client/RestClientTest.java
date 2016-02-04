package rest.task.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import rest.task.exceptions.UserNotFoundException;
import rest.task.model.Product;
import rest.task.model.Purchase;
import rest.task.model.Purchases;
import rest.task.model.User;
import rest.task.spring.RestClientTestConfig;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestClientTestConfig.class)
public class RestClientTest {

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private RestClient restClient;

    @Before
    public void init() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void shouldGetUserWithNameTestAndEmailTestAtGmailCom() throws UserNotFoundException {
        mockRestServiceServer.expect(requestTo("http://localhost:6000/api/users/test"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"user\":{\"username\":\"test\",\"email\":\"test@gmail.com\"}}", MediaType.APPLICATION_JSON));

        User user = restClient.findUserByName("test");

        mockRestServiceServer.verify();

        assertEquals("test", user.getUserName());
        assertEquals("test@gmail.com", user.getEmail());
    }

    @Test
    public void shouldGetPurchaseesByUserName() {
        mockRestServiceServer.expect(requestTo("http://localhost:6000/api/purchases/by_user/Hester46?limit=5"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"purchases\":[{\"id\":87905,\"username\":\"Hester46\",\"productId\":457792,\"date\":\"2015-12-15T09:21:45.604Z\"}," +
                        "{\"id\":724840,\"username\":\"Hester46\",\"productId\":210873,\"date\":\"2015-12-14T12:07:22.604Z\"}," +
                        "{\"id\":260364,\"username\":\"Hester46\",\"productId\":977593,\"date\":\"2015-12-14T15:20:38.604Z\"}," +
                        "{\"id\":249193,\"username\":\"Hester46\",\"productId\":855330,\"date\":\"2015-12-05T19:57:06.604Z\"}]}", MediaType.APPLICATION_JSON));

        Purchases purchases = restClient.getPopularPurchases("Hester46");

        mockRestServiceServer.verify();

        assertNotNull(purchases.getPurchases());;
        assertEquals(4, purchases.getPurchases().size());;

        assertPurchase(87905, "Hester46", 457792, "2015-12-15T09:21:45.604Z", purchases.getPurchases().get(0));
        assertPurchase(724840, "Hester46", 210873, "2015-12-14T12:07:22.604Z", purchases.getPurchases().get(1));
        assertPurchase(260364, "Hester46", 977593, "2015-12-14T15:20:38.604Z", purchases.getPurchases().get(2));
        assertPurchase(249193, "Hester46", 855330, "2015-12-05T19:57:06.604Z", purchases.getPurchases().get(3));
    }

    @Test
    public void shouldGetPurchasesById() {
        mockRestServiceServer.expect(requestTo("http://localhost:6000/api/purchases/by_product/855330"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"purchases\":[" +
                        "{\"id\":498575,\"username\":\"Kade6\",\"productId\":855330,\"date\":\"2015-12-11T14:15:39.602Z\"}," +
                        "{\"id\":724163,\"username\":\"Kade6\",\"productId\":855330,\"date\":\"2015-12-14T14:12:03.604Z\"}," +
                        "{\"id\":249193,\"username\":\"Hester46\",\"productId\":855330,\"date\":\"2015-12-05T19:57:06.604Z\"}," +
                        "{\"id\":841596,\"username\":\"Lilian.Schroeder19\",\"productId\":855330,\"date\":\"2015-12-13T14:57:14.605Z\"}," +
                        "{\"id\":625873,\"username\":\"Lilian.Schroeder19\",\"productId\":855330,\"date\":\"2015-12-11T10:55:25.606Z\"}," +
                        "{\"id\":227140,\"username\":\"Tom34\",\"productId\":855330,\"date\":\"2015-12-15T01:45:16.607Z\"}]}", MediaType.APPLICATION_JSON));

        Purchases purchases = restClient.getPurchases(855330);

        mockRestServiceServer.verify();

        assertNotNull(purchases.getPurchases());
        assertEquals(6, purchases.getPurchases().size());

        assertPurchase(498575, "Kade6", 855330, "2015-12-11T14:15:39.602Z", purchases.getPurchases().get(0));
        assertPurchase(724163, "Kade6", 855330, "2015-12-14T14:12:03.604Z", purchases.getPurchases().get(1));
        assertPurchase(249193, "Hester46", 855330, "2015-12-05T19:57:06.604Z", purchases.getPurchases().get(2));
        assertPurchase(841596, "Lilian.Schroeder19", 855330, "2015-12-13T14:57:14.605Z", purchases.getPurchases().get(3));
        assertPurchase(625873, "Lilian.Schroeder19", 855330, "2015-12-11T10:55:25.606Z", purchases.getPurchases().get(4));
        assertPurchase(227140, "Tom34", 855330, "2015-12-15T01:45:16.607Z", purchases.getPurchases().get(5));
    }

    @Test
    public void shouldGetProductById() {
        mockRestServiceServer.expect(requestTo("http://localhost:6000/api/products/457792"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"product\":{\"id\":457792,\"face\":\"(•ω•)\",\"price\":1102,\"size\":31}}", MediaType.APPLICATION_JSON));

        Product product = restClient.getProduct(457792);

        mockRestServiceServer.verify();

        assertEquals(457792, product.getId());
        assertEquals("(•ω•)", product.getFace());
        assertEquals(1102, product.getPrice());
        assertEquals(31, product.getSize());
    }

    private void assertPurchase(int expectedId, String expectedUserName, int expectedProductId, String expectedDate, Purchase actualPurchase) {
        assertEquals(expectedId, actualPurchase.getId());
        assertEquals(expectedProductId, actualPurchase.getProductId());
        assertEquals(expectedUserName, actualPurchase.getUserName());
        assertEquals(expectedDate, actualPurchase.getDate());
    }

}
