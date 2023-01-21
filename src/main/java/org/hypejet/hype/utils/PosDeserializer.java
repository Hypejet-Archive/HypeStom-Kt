package org.hypejet.hype.utils;

import com.google.gson.*;
import net.minestom.server.coordinate.Pos;

import java.lang.reflect.Type;

public class PosDeserializer implements JsonDeserializer<Pos> {
    @Override
    public Pos deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject jObject = json.getAsJsonObject();
        final double xValue = jObject.get("x").getAsDouble();
        final double yValue = jObject.get("y").getAsDouble();
        final double zValue = jObject.get("z").getAsDouble();
        final float yawValue = jObject.get("yaw").getAsFloat();
        final float pitchValue = jObject.get("pitch").getAsFloat();
        return new Pos(xValue, yValue, zValue, yawValue, pitchValue);
    }
}
