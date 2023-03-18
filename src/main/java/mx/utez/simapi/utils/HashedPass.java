package mx.utez.simapi.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class HashedPass {
    public static Boolean compare(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
