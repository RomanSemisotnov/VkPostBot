package myPackage.DAO;

import myPackage.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserDao extends AbstractDAO<User> {

    public UserDao() {
        setEntityClass(User.class);
    }

}
