package rest.task.spring;

import rest.task.client.RestClient;
import rest.task.controllers.PurchasesController;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PurchaseControllerTestConfig {

    @Bean
    public RestClient mockRestClient() {
        return Mockito.mock(RestClient.class);
    }

    @Bean
    public PurchasesController purchasesController() {
        return new PurchasesController();
    }


}
