package myPackage.config;

import myPackage.enums.Action;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class GlobalCollectionsConfig {

    /*
    key - user_id
    value - id of last attachment_ids
     */
    @Bean
    public ConcurrentHashMap<Integer, Integer> lastIncommingAttachmentsMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public ConcurrentHashMap<Integer, Map<Integer, Integer>> lastAttachmentsByOrderMap() {
        return new ConcurrentHashMap<Integer, Map<Integer, Integer>>();
    }

    /*
  key - user_id
  value - action, which user chould execute
   */
    @Bean
    public ConcurrentHashMap<Integer, Action> userActionMap() {
        return new ConcurrentHashMap<>();
    }

}
