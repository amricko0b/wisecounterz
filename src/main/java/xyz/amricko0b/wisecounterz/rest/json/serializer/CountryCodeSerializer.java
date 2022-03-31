package xyz.amricko0b.wisecounterz.rest.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.neovisionaries.i18n.CountryCode;

import java.io.IOException;

/**
 * Json serializer for {@link CountryCode}.
 *
 * @author amricko0b
 */
public class CountryCodeSerializer extends JsonSerializer<CountryCode> {

    @Override
    public void serialize(CountryCode value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        // We don't use UNDEFINED cause it makes no sense to domain logic.
        // While normally working - must not happen!
        if (CountryCode.UNDEFINED.equals(value)) {
            throw new IOException("CountryCode.UNDEFINED must not be used in JSON");
        }

        gen.writeFieldName(value.getAlpha2().toLowerCase());
    }
}
