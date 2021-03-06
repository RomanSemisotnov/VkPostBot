package myPackage.config;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Hashtable;

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
        return new VkApiClient(HttpTransportClient.getInstance());
    }

    @Bean
    public GroupActor myGroupActor() {
        return new GroupActor(Integer.parseInt(environment.getProperty("vk.myGroupId")), environment.getProperty("vk.access_token"));
    }

}
