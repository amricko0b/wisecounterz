package xyz.amricko0b.wisecounterz.domain;

import com.neovisionaries.i18n.CountryCode;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

public class CountryCodeFactoryImplTestCase implements WithAssertions {

    final CountryCodeFactory factory = new CountryCodeFactoryImpl();

    @Test
    void test_createCountryCodeFrom_validRawCode() {

        assertThat(factory.createBy("cy"))
                .isEqualTo(CountryCode.CY)
                .describedAs("Service creates valid enum value by valid provided code");
    }

    @Test
    void test_createCountryCodeFrom_validRawCode_caseInsensitive() {

        assertThat(factory.createBy("Ru"))
                .isEqualTo(CountryCode.RU)
                .describedAs("Service creates valid enum value by Ru ignoring case");

        assertThat(factory.createBy("US"))
                .isEqualTo(CountryCode.US)
                .describedAs("Service creates valid enum value by US ignoring case");

        assertThat(factory.createBy("cY"))
                .isEqualTo(CountryCode.CY)
                .describedAs("Service creates valid enum value by cY ignoring case");
    }

    @Test
    void test_createCountryCodeFrom_invalidRawCode() {

        assertThatThrownBy(() -> factory.createBy("xy"))
                .isInstanceOf(NoSuchCountryException.class)
                .hasMessage("There is no such country: xy")
                .describedAs("Service fails with proper exception for invalid raw country code");

        assertThatThrownBy(() -> factory.createBy("c"))
                .isInstanceOf(NoSuchCountryException.class)
                .hasMessage("There is no such country: c")
                .describedAs("Service fails with proper exception for too short raw country code");

        assertThatThrownBy(() -> factory.createBy("usa"))
                .isInstanceOf(NoSuchCountryException.class)
                .hasMessage("There is no such country: usa")
                .describedAs("Service fails with proper exception for too long raw country code");
    }
}
