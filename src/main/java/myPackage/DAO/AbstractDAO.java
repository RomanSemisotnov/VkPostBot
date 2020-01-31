package myPackage.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@Repository
public abstract class AbstractDAO<T> {

    private Class<T> entityClass;

    @Autowired
    protected SessionFactory sessionFactory;

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<T> find(TripleConsumer<CriteriaQuery<T>, CriteriaBuilder, Root> selecter) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> root = criteria.from(entityClass);

        selecter.accept(criteria, builder, root);

        return session.createQuery(criteria).list();
    }

    public T findOne(TripleConsumer<CriteriaQuery<T>, CriteriaBuilder, Root> selecter) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> root = criteria.from(entityClass);

        selecter.accept(criteria, builder, root);

        return session.createQuery(criteria).stream().findFirst().orElse(null);
    }

    public T findById(int id) {
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
        T entity = findById(id);
        delete(entity);
    }

}
