package xyz.amricko0b.wisecounterz.domain;

import com.neovisionaries.i18n.CountryCode;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

public class CountryCodeSanitizerImplTestCase implements WithAssertions {

    final CountryCodeSanitizer factory = new CountryCodeSanitizerImpl();

    @Test
    void test_createCountryCodeFrom_validRawCode() {

        assertThat(factory.sanitize("cy"))
                .isEqualTo(CountryCode.CY)
                .describedAs("Service creates valid enum value by valid provided code");
    }

    @Test
    void test_createCountryCodeFrom_validRawCode_caseInsensitive() {

        assertThat(factory.sanitize("Ru"))
                .isEqualTo(CountryCode.RU)
                .describedAs("Service creates valid enum value by Ru ignoring case");

        assertThat(factory.sanitize("US"))
                .isEqualTo(CountryCode.US)
                .describedAs("Service creates valid enum value by US ignoring case");

        assertThat(factory.sanitize("cY"))
                .isEqualTo(CountryCode.CY)
                .describedAs("Service creates valid enum value by cY ignoring case");
    }

    @Test
    void test_createCountryCodeFrom_invalidRawCode() {

        assertThatThrownBy(() -> factory.sanitize("xy"))
                .isInstanceOf(NoSuchCountryException.class)
                .hasMessage("There is no such country: xy")
                .describedAs("Service fails with proper exception for invalid raw country code");

        assertThatThrownBy(() -> factory.sanitize("c"))
                .isInstanceOf(NoSuchCountryException.class)
                .hasMessage("There is no such country: c")
                .describedAs("Service fails with proper exception for too short raw country code");

        assertThatThrownBy(() -> factory.sanitize("usa"))
                .isInstanceOf(NoSuchCountryException.class)
                .hasMessage("There is no such country: usa")
                .describedAs("Service fails with proper exception for too long raw country code");
    }
}
