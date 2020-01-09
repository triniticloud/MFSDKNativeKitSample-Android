package ai.active.uikit.template.util;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public class JsonUtil {


    /**
     * Return true if given JsonObject contains key and key value is not null
     *
     * @param jo {@link JsonObject} to check
     * @param key Key to find in passed JsonObject
     * @return true if JsonObject contains key, else false
     */
    public static boolean has(JsonObject jo, String key) {
        return jo != null && jo.has(key) && !(jo.get(key) instanceof JsonNull);

    }

}
