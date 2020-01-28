package myPackage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class GlobalCollectionsConfig {

    /*
    key - user_id
    value - list of last attachment_ids
     */
    @Bean
    public ConcurrentHashMap<Integer, List<Integer>> lastIncommingAttachmentsMap() {
        return new ConcurrentHashMap<>();
    }

}
