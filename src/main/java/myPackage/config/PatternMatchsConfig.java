package myPackage.config;

import myPackage.enums.Command;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

@Configuration
public class PatternMatchsConfig {

    @Bean
    public Pattern addTopicCommandPattern() {
        return Pattern.compile("^\\s*[" + Command.ADD_TOPIC.getValue() + "]\\s*[\\S+\\s*]+$");
    }

    @Bean
    public Pattern extraSpacePattern() {
        return Pattern.compile("[\\s]{2,}");
    }

}
