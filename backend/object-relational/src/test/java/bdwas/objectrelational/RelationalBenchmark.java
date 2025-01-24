package bdwas.objectrelational;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import bdwas.benchmark.BaseBenchmark;
import bdwas.benchmark.Identifiable;
import bdwas.models.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class RelationalBenchmark<R extends JpaRepository<E, I>, E extends Identifiable<I>, I> extends BaseBenchmark<E> {

    private static Object repository;
    protected static EntityManager entityManager;

    @Override
    protected Collection<E> addEntities(Collection<E> entities) {
        return getRepository().saveAll(entities);
    }

    @Override
    protected Collection<E> updateEntities(Collection<E> entities) {
        entities.forEach(this::updateEntity);
        return getRepository().saveAll(entities);
    }

    protected abstract void updateEntity(E entity);

    @Override
    protected void deleteEntities(Collection<E> entities) {
        List<I> idList = entities.stream()
                                         .map(Identifiable::getId)
                                         .toList();
        getRepository().deleteAllByIdInBatch(idList);
    }

    /**
     * Get the table name from a JPA entity class.
     */
    protected String getTableName(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            if (!table.name().isEmpty()) {
                return table.name();
            }
        }
        // Default to class name if @Table is not present or name is not explicitly defined
        return entityClass.getSimpleName();
    }

    @Override
    protected void clearData() {
        entityManager.getTransaction().begin();

        getTables().forEach(
                entityClass -> entityManager
                        .createNativeQuery("TRUNCATE TABLE " + getTableName(entityClass) + " " + "CASCADE")
                        .executeUpdate());

        entityManager.getTransaction().commit();
    }

    protected abstract Iterable<Class<? extends BaseEntity<I>>> getTables();

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
