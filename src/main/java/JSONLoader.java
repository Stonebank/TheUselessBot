
import com.google.gson.*;

import java.io.FileReader;
import java.nio.file.Paths;

public abstract class JSONLoader {


    /**
     * Allows the user to read and/or modify the parsed data.
     *
     * @param reader  the reader instance.
     * @param builder the builder instance.
     */
    public abstract void load(JsonObject reader, Gson builder);

    /**
     * Returns the path to the <code>.json</code> file that will be parsed.
     *
     * @return the path to the file.
     */
    public abstract String filePath();

    /**
     * Loads the parsed data. How the data is loaded is defined by
     * <code>load(JsonObject j, Gson g)</code>.
     *
     * @return the loader instance, for chaining.
     * @throws Exception if any exception occur while loading the parsed data.
     */
    public JSONLoader load() {
        try (FileReader fileReader = new FileReader(Paths.get(filePath()).toFile())) {
            JsonArray array = (JsonArray) JsonParser.parseReader(fileReader);
            Gson builder = new GsonBuilder().create();
            for (int i = 0; i < array.size(); i++) {
                JsonObject reader = (JsonObject) array.get(i);
                load(reader, builder);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}