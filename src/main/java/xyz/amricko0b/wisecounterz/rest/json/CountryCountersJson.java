package xyz.amricko0b.wisecounterz.rest.json;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.neovisionaries.i18n.CountryCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * A JSON object that treats country codes as keys and counters as values
 *
 * @author amricko0b
 */
@Schema(description = "Available country counters info", example = "{\"cy\": 9, \"ru\": 5}")
@AllArgsConstructor
public class CountryCountersJson {

    private Map<CountryCode, Long> counters;

    @JsonAnyGetter
    public Map<CountryCode, Long> getCounters() {
        return counters;
    }
}
