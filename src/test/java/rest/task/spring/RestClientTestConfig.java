package rest.task.spring;

import rest.task.client.ClientConfiguration;
import rest.task.client.RestClient;
import rest.task.client.RestClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:/application.properties")
public class RestClientTestConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean
    public RestClient restClient() {
        return new RestClientImpl();
    }

    @Bean
    public ClientConfiguration configuration() {
        return new ClientConfiguration();
    }
}
