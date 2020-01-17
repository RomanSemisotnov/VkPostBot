package myPackage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "classpath:vkAuth.properties")
public class VkConfig {

    private Environment environment;
    private String secret;

    public VkConfig(Environment environment) {
        this.environment = environment;
        secret = environment.getProperty("vk.secret");
    }

    public String getSecret() {
        return secret;
    }

}
