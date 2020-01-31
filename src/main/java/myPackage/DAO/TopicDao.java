package myPackage.DAO;

import myPackage.entities.Topic;
import org.springframework.stereotype.Repository;

@Repository
public class TopicDao extends AbstractDAO<Topic> {

    public TopicDao() {
        setEntityClass(Topic.class);
    }

}
