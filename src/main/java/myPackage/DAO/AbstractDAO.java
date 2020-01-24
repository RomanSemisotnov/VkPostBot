package myPackage.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Consumer;

@Component
public abstract class AbstractDAO<T> {

    private Class<T> entityClass;

    @Autowired
    protected SessionFactory sessionFactory;

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T findOne(int id) {
        return (T) sessionFactory.openSession().get(entityClass, id);
    }

    public List<T> findAll() {
        return sessionFactory.openSession().createQuery("from " + entityClass.getName()).list();
    }

    public void save(T entity) {
        sessionFactory.openSession().save(entity);
    }

    public void update(List<Integer> ids, Consumer<CriteriaUpdate<T>> updateConsumer) {
        Session session = sessionFactory.openSession();
        Transaction tr = session.beginTransaction();
        CriteriaUpdate<T> update = session.getCriteriaBuilder().createCriteriaUpdate(entityClass);
        Root<T> root = update.from(entityClass);

        updateConsumer.accept(update);

        update.where(root.get("id").in(ids));
        session.createQuery(update).executeUpdate();
        tr.commit();
    }

    public void delete(T entity) {
        sessionFactory.openSession().delete(entity);
    }

    public void deleteById(int id) {
        T entity = findOne(id);
        delete(entity);
    }

}
