package bdwas.mongo;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.ListCrudRepository;

@SpringBootTest
@State(Scope.Benchmark)
public abstract class RepositoryBenchmarkBase<R extends ListCrudRepository<E, I>, E, I> {

    private static Object repository;

    protected Faker faker = new Faker(Locale.getDefault());

    @SuppressWarnings("unused")
    @Param({"1", "100", "2000", "4000", "8000", "20000"})
    private int recordCount;

    protected Collection<E> existingEntities;
    protected Collection<E> entities;

    @Setup(Level.Iteration)
    public void setup() {
        clearTables();
        existingEntities = getRepository().saveAll(generateEntities(recordCount));
        entities = generateEntities(recordCount);
    }

    @Benchmark
    public void benchmarkAdd() {
        getRepository().saveAll(entities);
    }

    @Benchmark
    public void benchmarkUpdate() {
        updateEntities(existingEntities);
        getRepository().saveAll(existingEntities);
    }

    @Benchmark
    public void benchmarkDelete() {
        getRepository().deleteAll(existingEntities);
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

    protected abstract E generateEntity();

    protected abstract void updateEntities(Collection<E> existingEntities);

    protected abstract void clearTables();

    protected Collection<E> generateEntities(int count) {
        return Stream.generate(this::generateEntity)
                     .limit(count)
                     .toList();
    }

    /**
     * Any benchmark, by extending this class, inherits this single @Test method for JUnit to run.
     */
    @Test
    public void executeBenchmark() throws RunnerException {
        int warmup = 0;
        int iterations = 10;

        Options opt = new OptionsBuilder()
                .include(AccountRepositoryTest.class.getSimpleName())
                .warmupIterations(warmup)
                .measurementIterations(iterations)
                .warmupTime(TimeValue.NONE)
                .measurementTime(TimeValue.NONE)
                .forks(0)
                .threads(1)
                .timeUnit(TimeUnit.SECONDS)
                .mode(Mode.AverageTime)
                .result("benchmark.text")
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .resultFormat(ResultFormatType.TEXT)
                .shouldFailOnError(true)
                .build();

        new Runner(opt).run();

        Assertions.assertTrue(true);
    }
}

