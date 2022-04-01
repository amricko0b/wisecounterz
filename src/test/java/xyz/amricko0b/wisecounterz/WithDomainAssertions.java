package xyz.amricko0b.wisecounterz;

import com.neovisionaries.i18n.CountryCode;
import org.assertj.core.api.Condition;
import org.assertj.core.api.ThrowingConsumer;
import org.assertj.core.api.WithAssertions;
import xyz.amricko0b.wisecounterz.data.CountryCounter;

public interface WithDomainAssertions extends WithAssertions {

    default <T extends CountryCounter> ThrowingConsumer<T> counterCriteria(CountryCode expectedCountryCode, Long expectedCounterValue) {
        return counter -> {
            assertThat(counter).is(forCountry(expectedCountryCode));
            assertThat(counter).has(counterValue(expectedCounterValue));
        };
    }

    default <T extends CountryCounter> Condition<T> forCountry(CountryCode countryCode) {
        return new Condition<>(
                counter -> countryCode.equals(counter.getCountryCode()),
                "counter for " + countryCode.getAlpha2()
        );
    }

    default <T extends CountryCounter> Condition<T> counterValue(Long countryCode) {
        return new Condition<>(
                counter -> countryCode.equals(counter.getCounter()),
                "counter value of " + countryCode
        );
    }

}
