package xyz.amricko0b.wisecounterz.data.jpa;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import xyz.amricko0b.wisecounterz.WithDomainAssertions;
import xyz.amricko0b.wisecounterz.data.jpa.repository.CountryCounterRepository;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CountryCounterJpaManagerIntegrationTestCase extends AbstractJpaTestCase implements WithDomainAssertions {

    @Autowired
    private CountryCounterJpaManager manager;

    @Autowired
    private CountryCounterRepository repository;

    @Autowired
    private TransactionTemplate inTransaction;

    @Test
    void test_findAll_forNoCounter_returnsNothing() {
        final var counters = manager.findAll();
        assertThat(counters).hasSize(0);
    }

    @Test
    void test_increment_forNonExisted_createsCounter() {

        // Increment for non-existed counters will create them
        inTransaction.executeWithoutResult(tx -> {
            manager.incrementBy(CountryCode.CY);
            manager.incrementBy(CountryCode.US);
            manager.incrementBy(CountryCode.RU);
        });

        final var counters = manager.findAll();

        // Initial value must be 1
        assertThat(counters).hasSize(3)
                .anySatisfy(counterCriteria(CountryCode.CY, 1L))
                .anySatisfy(counterCriteria(CountryCode.US, 1L))
                .anySatisfy(counterCriteria(CountryCode.RU, 1L));
    }

    @Test
    void test_subsequentIncrements_workFine() {
        // Increment non-existed counter. That will create a counter with a value 1
        inTransaction.executeWithoutResult(tx -> {
            manager.incrementBy(CountryCode.CY);
            tx.flush();
        });

        // Increment existed counter once more.
        // We do this in separate transaction
        inTransaction.executeWithoutResult(tx -> {
            manager.incrementBy(CountryCode.CY);
            tx.flush();
        });

        // Check current value.
        // We do this in separate transaction to make actual value visible
        final var actual = inTransaction.execute(tx -> {
            final var counter = repository.findById(CountryCode.CY);
            tx.flush();
            return counter.get();
        });

        assertThat(actual).has(counterValue(2L));
    }

    @Test
    void test_concurrentIncrements_workConsistently() throws InterruptedException {

        // To emulate concurrent load we need thread pool
        final var executor = Executors.newFixedThreadPool(5);

        // Submit concurrent transactions
        IntStream.range(1, 6).forEach(i -> {
            executor.execute(() -> {
                inTransaction.execute(tx -> {
                    final var current = manager.incrementBy(CountryCode.US);
                    tx.flush();
                    return current;
                });
            });
        });

        executor.awaitTermination(5L, TimeUnit.SECONDS);

        final var afterAll = inTransaction.execute(tx -> {
            final var counter = repository.findById(CountryCode.US).get();
            tx.flush();
            return counter;
        });

        assertThat(afterAll).has(counterValue(5L));
    }
}
