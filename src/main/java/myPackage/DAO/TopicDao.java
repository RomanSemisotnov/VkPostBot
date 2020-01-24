package myPackage.DAO;

import myPackage.entities.Topic;
import org.springframework.stereotype.Component;

@Component
public class TopicDao extends AbstractDAO<Topic> {

    public TopicDao() {
        setEntityClass(Topic.class);
    }

}
