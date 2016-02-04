package rest.task.spring;

import rest.task.client.ClientConfiguration;
import rest.task.client.RestClient;
import rest.task.client.RestClientImpl;
import rest.task.controllers.PurchasesController;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;
import rest.task.client.ErrorsController;

@Configuration
@ComponentScan({"java.task"})
@EnableCaching
@PropertySource("classpath:/application.properties")
public class AppConfig {

//    @Value("${api.endpoint}")
//    private String apiEndpoint;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public RestClient getRestClient() {
        return new RestClientImpl();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean
    public ClientConfiguration getClientConfiguration() {
        return new ClientConfiguration();
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }

    @Bean
    public PurchasesController purchasesController() {
        return new PurchasesController();
    }

    @Bean
    public ErrorsController errorsController() {
        return new ErrorsController();
    }
}
