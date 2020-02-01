package myPackage.DAO;

import myPackage.entities.Attachment;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttachmentDao extends AbstractDAO<Attachment> {

    public AttachmentDao() {
        setEntityClass(Attachment.class);
    }

    public List<Attachment> getNotRead(Integer topicId) {
        Session session = sessionFactory.openSession();
        Query<Attachment> query = session.createQuery("select attachment from Attachment attachment " +
                "where attachment.topicId = :topicId and attachment.isRead = false");
        query.setParameter("topicId", topicId);

        return query.list();
    }

    public Attachment getByTypeAndOwnerIdAndVkId(String type, int ownedId, int vkId) {
        return findOne((criteriaQuery, builder, root) -> {
            criteriaQuery
                    .where(builder.equal(root.get("isRead"), false))
                    .where(builder.equal(root.get("type"), type))
                    .where(builder.equal(root.get("ownerId"), ownedId))
                    .where(builder.equal(root.get("vkId"), vkId));
        });
    }

}
