package mx.utez.simapi.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class HashedPass {
    public static Boolean compare(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
