package mx.utez.simapi.utils;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EmailValidator {
    public static boolean validation(String correo) {
        String regex = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }
}
