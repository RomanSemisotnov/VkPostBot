package myPackage.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
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

    public Integer save(T entity) {
        return (Integer) sessionFactory.openSession().save(entity);
    }

    public List<Integer> saveAll(Collection<T> entities) {
        Session session = sessionFactory.openSession();
        List<Integer> ids = new ArrayList<>();
        for (T entity : entities) {
            ids.add((Integer) session.save(entity));
        }
        return ids;
    }

    public int update(int id, Consumer<CriteriaUpdate<T>> updateConsumer) {
        List<Integer> ids = new ArrayList<>();
        ids.add(id);
        return update(ids, updateConsumer);
    }

    public int update(List<Integer> ids, Consumer<CriteriaUpdate<T>> updateConsumer) {
        Session session = sessionFactory.openSession();
        Transaction tr = session.beginTransaction();
        CriteriaUpdate<T> update = session.getCriteriaBuilder().createCriteriaUpdate(entityClass);
        Root<T> root = update.from(entityClass);

        updateConsumer.accept(update);

        update.where(root.get("id").in(ids));
        int updatedCount = session.createQuery(update).executeUpdate();
        tr.commit();
        return updatedCount;
    }

    public void delete(T entity) {
        sessionFactory.openSession().delete(entity);
    }

    public void deleteById(int id) {
        T entity = findOne(id);
        delete(entity);
    }

}
