package mx.utez.simapi.controllers.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/institucion")
public class InstitucionAuthController {
    @GetMapping
    public String get() {
        return "Institucion";
    }
}
