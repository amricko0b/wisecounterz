package xyz.amricko0b.wisecounterz.domain;

import com.neovisionaries.i18n.CountryCode;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;
import xyz.amricko0b.wisecounterz.data.CountryCounter;
import xyz.amricko0b.wisecounterz.data.CountryCounterFactory;
import xyz.amricko0b.wisecounterz.data.CountryCounterManager;

import java.util.Set;

import static org.mockito.Mockito.when;

@MockitoSettings
public class CountryCounterServiceImplTestCase implements WithAssertions {

    private CountryCounterFactory<TestCountryCounter> factory = new TestCountryCounterFactory();

    @Spy
    private CountryCodeSanitizer countryCodeSanitizer = new CountryCodeSanitizerImpl();

    @Mock
    private CountryCounterManager managerMock;

    @InjectMocks
    private CountryCounterServiceImpl service;

    @Test
    void test_getAllCounters_notPresent() {
        when(managerMock.findAll()).thenReturn(Set.of());
        assertThat(service.getAllCounters()).hasSize(0);
    }

    @Test
    void test_getAllCounters_onePresent() {
        when(managerMock.findAll()).thenReturn(
                Set.of(factory.createInitial(CountryCode.CY))
        );

        final var counters = service.getAllCounters();
        assertThat(counters).hasSize(1);
        assertThat(counters.get(CountryCode.CY)).isEqualTo(1L);
    }

    @Test
    void test_getAllCounters_multiplePresent() {
        when(managerMock.findAll()).thenReturn(
                Set.of(
                        factory.createInitial(CountryCode.CY),
                        factory.createInitial(CountryCode.US),
                        factory.createInitial(CountryCode.RU)
                )
        );

        final var counters = service.getAllCounters();
        assertThat(counters).hasSize(3);
        assertThat(counters.get(CountryCode.CY)).isEqualTo(1L);
        assertThat(counters.get(CountryCode.US)).isEqualTo(1L);
        assertThat(counters.get(CountryCode.RU)).isEqualTo(1L);
    }

    @Test
    void test_incrementByWrongCode_fails() {
        assertThatThrownBy(() -> service.incrementCounterByRaw("XYZ"))
                .isInstanceOf(NoSuchCountryException.class)
                .hasMessage("There is no such country: XYZ");
    }

    @Test
    void test_incrementByCode_worksFine() {

        when(managerMock.incrementBy(CountryCode.DE)).thenReturn(9L);
        final var result = service.incrementCounterByRaw("de");
        assertThat(result.getFirst()).isEqualTo(CountryCode.DE);
        assertThat(result.getSecond()).isEqualTo(9L);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(managerMock);
    }

    static class TestCountryCounter extends CountryCounter {
    }

    static class TestCountryCounterFactory extends CountryCounterFactory<TestCountryCounter> {
        @Override
        public TestCountryCounter allocate() {
            return new TestCountryCounter();
        }
    }
}
