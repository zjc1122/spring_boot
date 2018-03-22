package cn.zjc.util;

import com.google.gson.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by zhangjiacheng on 2018/3/21.
 */
public class GsonHolder {

    private static final Gson GSON = new Gson();
    private static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Gson SERIALIZE_NULLS_GSON = new GsonBuilder().serializeNulls().create();

    private static final Gson LOCALDATE_GSON = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .create();

    private static final Gson DATE_GSON = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
            .create();

    private GsonHolder() {
    }

    public static Gson getGson() {
        return GSON;
    }

    public static Gson getPrettyFormatGson() {
        return PRETTY_GSON;
    }

    public static Gson getSerializeNullsGson() {
        return SERIALIZE_NULLS_GSON;
    }

    public static Gson getLocalDateGson() {
        return LOCALDATE_GSON;
    }
    public static Gson getDateGson() {
        return DATE_GSON;
    }
}
