package myPackage.config;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "classpath:vkAuth.properties")
public class VkConfig {

    private Environment environment;
    private String secret;
    private String access_token;

    public VkConfig(Environment environment) {
        this.environment = environment;
        secret = environment.getProperty("vk.secret");
        access_token = environment.getProperty("vk.access_token");
    }

    public String getSecret() {
        return secret;
    }

    public String getAccessToken() {
        return access_token;
    }

    @Bean
    public VkApiClient vkApiClient() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        return new VkApiClient(transportClient);
    }

    @Bean
    public GroupActor myGroupActor() {
        return new GroupActor(Integer.parseInt(environment.getProperty("vk.myGroupId")), environment.getProperty("vk.access_token"));
    }

}
