package myPackage.DAO;

import myPackage.entities.IncommingMessage;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

@Component
public class IncommingMessageDao extends AbstractDAO<IncommingMessage> {

    public IncommingMessageDao() {
        setEntityClass(IncommingMessage.class);
    }

    public IncommingMessage getLastByUserId(int user_id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select message from IncommingMessage message " +
                "where message.user_id = :user_id order by message.id desc ");
        query.setParameter("user_id", user_id);
        query.setMaxResults(1);

        return (IncommingMessage) query.stream().findFirst().orElse(null);
    }

}
