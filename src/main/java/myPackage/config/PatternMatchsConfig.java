package myPackage.config;

import myPackage.enums.Action;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

@Configuration
public class PatternMatchsConfig {

    @Bean
    public Pattern addTopicCommandPattern() {
        return Pattern.compile("^\\s*[" + Action.ADD_TOPIC_BY_COMMAND.getCommand() + "]\\s*[\\S+\\s*]+$");
    }

    @Bean
    public Pattern getTopicCommandPattern() {
        return Pattern.compile("^\\s*[" + Action.GET_TOPICS_BY_COMMAND.getCommand() + "]\\s*[\\S+\\s*]+$");
    }

    @Bean
    public Pattern anyCommandPattern() {
        String allCommands = "(";
        for (Action command : Action.values()) {
            if (command.getCommand() != null)
                allCommands += command.getCommand() + "|";
        }
        allCommands = allCommands.substring(0, allCommands.length() - 1);
        allCommands += ")";
        return Pattern.compile("^\\s*[" + allCommands + "]\\s*[\\S+\\s*]+$");
    }

    @Bean
    public Pattern extraSpacePattern() {
        return Pattern.compile("[\\s]{2,}");
    }

}
