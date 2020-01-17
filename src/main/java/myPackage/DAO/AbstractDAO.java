package myPackage.DAO;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AbstractDAO<T> {

    private Class<T> entityClass;

    @Autowired
    protected SessionFactory sessionFactory;

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T findOne(long id) {
        return (T) sessionFactory.openSession().get(entityClass, id);
    }

    public List<T> findAll() {
        return sessionFactory.openSession().createQuery("from " + entityClass.getName()).list();
    }

    public void save(T entity) {
        sessionFactory.openSession().persist(entity);
    }

    public T update(T entity) {
        return (T) sessionFactory.openSession().merge(entity);
    }

    public void delete(T entity) {
        sessionFactory.openSession().delete(entity);
    }

    public void deleteById(long id) {
        T entity = findOne(id);
        delete(entity);
    }

}
