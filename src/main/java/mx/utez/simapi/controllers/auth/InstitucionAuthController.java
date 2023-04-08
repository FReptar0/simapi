package mx.utez.simapi.controllers.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.controllers.InstitucionController;
import mx.utez.simapi.models.Colores;
import mx.utez.simapi.models.Institucion;
import mx.utez.simapi.models.UsuarioLogin;
import mx.utez.simapi.repository.ColoresRepository;
import mx.utez.simapi.repository.InstitucionRepository;
import mx.utez.simapi.utils.CustomResponse;

@RestController
@RequestMapping("/api/auth/institucion")
public class InstitucionAuthController {
    @Autowired
    InstitucionRepository institucionRepository;

    @Autowired
    InstitucionController institucionController;

    @Autowired
    ColoresRepository coloresRepository;

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping
    public ResponseEntity<CustomResponse<Object>> login(@RequestBody UsuarioLogin usuarioLogin) {
        Institucion institucion = institucionRepository.findByCorreo(usuarioLogin.getCorreo());
        CustomResponse<Object> response = new CustomResponse<>();
        try {
            if (institucion == null) {
                response.setError(true);
                response.setStatusCode(401);
                response.setMessage("Usuario o contraseña incorrectos");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else if (!encoder.matches(usuarioLogin.getPassword(), institucion.getPassword())) {
                response.setError(true);
                response.setStatusCode(401);
                response.setMessage("Usuario o contraseña incorrectos");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Login exitoso");

                Map<String, Object> jsonMap = new HashMap<>();
                jsonMap.put("estado", true);

                Colores color = coloresRepository.findByIdInstitucion(institucion.getIdInstitucion());
                jsonMap.put("color", color);

                response.setData(jsonMap);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(500);
            response.setMessage("Error interno del servidor");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
