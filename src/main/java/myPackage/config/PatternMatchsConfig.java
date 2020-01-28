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
    public Pattern anyCommandPattern() {
        String allCommands = "(";
        for (Command command : Command.values()) {
            allCommands += command.getValue() + "|";
        }
        allCommands = allCommands.substring(0, allCommands.length() - 1);
        allCommands += ")";
        return Pattern.compile("^\\s*[" + allCommands + "]\\s*[\\S+\\s*]+$");
    }

    @Bean
    public Pattern commaPattern() {
        return Pattern.compile("\\.");
    }

    @Bean
    public Pattern extraSpacePattern() {
        return Pattern.compile("[\\s]{2,}");
    }

}
