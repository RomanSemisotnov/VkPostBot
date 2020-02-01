package myPackage.DAO;

import myPackage.entities.Topic;
import org.springframework.stereotype.Repository;

@Repository
public class TopicDao extends AbstractDAO<Topic> {

    public TopicDao() {
        setEntityClass(Topic.class);
    }

    public Integer findDefault(int userId) {
        Topic topic = findOne((criteriaQuery, builder, root) -> {
            criteriaQuery
                    .where(builder.equal(root.get("userId"), userId))
                    .where(builder.equal(root.get("name"), "Без темы"));
        });

        Integer topicId;
        if (topic == null)
            topicId = save(new Topic("Без темы", userId));
        else
            topicId = topic.getId();

        return topicId;
    }

}
