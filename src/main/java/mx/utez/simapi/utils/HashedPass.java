package mx.utez.simapi.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class HashedPass {
    public static boolean check(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
