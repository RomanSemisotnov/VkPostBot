package myPackage.DAO;

import myPackage.entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractDAO<User> {

    public UserDao() {
        setEntityClass(User.class);
    }

    public User findOrCreateByVkId(int vkId) {
        Session session = sessionFactory.openSession();
        Query<User> query = session.createQuery("select user from User user where user.vkId = :vk_id");
        query.setParameter("vk_id", vkId);

        User user = query.getSingleResult();
        if(user == null){
            User newUser = new User(vkId);
            session.save(newUser);
            return newUser;
        }else{
            return user;
        }

    }

}
