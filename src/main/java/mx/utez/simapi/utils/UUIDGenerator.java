package mx.utez.simapi.utils;

import java.util.UUID;

public class UUIDGenerator {

    public static String getId() {
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }

}
