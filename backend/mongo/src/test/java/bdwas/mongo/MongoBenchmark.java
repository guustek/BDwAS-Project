package bdwas.mongo;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import bdwas.benchmark.BaseBenchmark;
import bdwas.benchmark.Identifiable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.MongoRepository;

public abstract class MongoBenchmark<R extends MongoRepository<E, I>, E extends Identifiable<I>, I> extends BaseBenchmark<E> {

    private static Object repository;

    @Override
    protected Collection<E> addEntities(Collection<E> entities) {
        getRepository().saveAll(entities);
        return entities;
    }

    @Override
    protected Collection<E> updateEntities(Collection<E> entities) {
        entities.forEach(this::updateEntity);
        getRepository().saveAll(entities);
        return entities;
    }

    protected abstract void updateEntity(E entity);

    @Override
    protected void deleteEntities(Collection<E> entities) {
        List<I> idList = entities.stream().map(Identifiable::getId).toList();
        getRepository().deleteAllById(idList);
    }

    @Override
    protected void clearData() {
        getRepository().deleteAll();
    }

    @Autowired
    private void setRepository(final ApplicationContext context) {
        Class<R> repositoryType = getRepositoryType();
        repository = context.getBean(repositoryType);
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
