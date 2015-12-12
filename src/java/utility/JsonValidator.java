package utility;

import java.io.IOException;
import java.io.InputStream;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonValidator {

    public void validateJson(String jsonString) throws IOException, ValidationException {
        try (InputStream inputStream = getClass().getResourceAsStream("/utility/JsonSchema.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(new JSONObject(jsonString)); // throws a ValidationException if this object is invalid
        }
    }

}
