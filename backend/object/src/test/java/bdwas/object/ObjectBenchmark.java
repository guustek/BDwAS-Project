package bdwas.object;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import bdwas.benchmark.BaseBenchmark;
import bdwas.benchmark.Identifiable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class ObjectBenchmark<R extends JpaRepository<E, I>, E extends Identifiable<I>, I> extends BaseBenchmark<E> {

    private static Object repository;
    protected static EntityManager entityManager;

    @Override
    protected Collection<E> addEntities(Collection<E> entities) {
        entityManager.getTransaction().begin();
        entities.forEach(entity -> entityManager.persist(entity));
        entityManager.getTransaction().commit();
        return entities;
    }

    @Override
    protected Collection<E> updateEntities(Collection<E> entities) {
        entityManager.getTransaction().begin();
        entities.forEach(entity -> {
            updateEntity(entity);
            entityManager.merge(entity);
        });
        entityManager.getTransaction().commit();
        return entities;
    }

    protected abstract void updateEntity(E entity);

    @Override
    protected void deleteEntities(Collection<E> entities) {
        List<I> idList = entities.stream()
                                         .map(Identifiable::getId)
                                         .toList();
        getRepository().deleteAllByIdInBatch(idList);
    }

    @Override
    protected void clearData() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Account").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Autowired
    private void setRepository(final ApplicationContext context) {
        Class<R> repositoryType = getRepositoryType();
        repository = context.getBean(repositoryType);
        entityManager = context.getBean(EntityManagerFactory.class).createEntityManager();
    }

    @SuppressWarnings("unchecked")
    private Class<R> getRepositoryType() {
        return (Class<R>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    protected R getRepository() {
        return (R) repository;
    }
}
