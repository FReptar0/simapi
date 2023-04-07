package mx.utez.simapi.controllers.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.controllers.InstitucionController;
import mx.utez.simapi.models.Institucion;
import mx.utez.simapi.models.UsuarioLogin;
import mx.utez.simapi.repository.InstitucionRepository;
import mx.utez.simapi.utils.CustomResponse;
import mx.utez.simapi.utils.HashedPass;

@RestController
@RequestMapping("/api/auth/institucion")
@CrossOrigin(origins = "*")
public class InstitucionAuthController {
    @Autowired
    InstitucionRepository institucionRepository;

    @Autowired
    InstitucionController institucionController;

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping
    public ResponseEntity<CustomResponse<Object>> login(@RequestBody UsuarioLogin usuarioLogin) {
        Institucion usuario = institucionRepository.findByCorreo(usuarioLogin.getCorreo());
        System.out.println("Usuario: " + usuario.getPassword());
        CustomResponse<Object> response = new CustomResponse<>();
        if (usuario != null) {
            try {
                System.out.println("Usuario: " + usuarioLogin.getPassword());
                Boolean valid = encoder.matches(usuarioLogin.getPassword(), usuario.getPassword());
                System.out.println("Valido: " + valid);
                if (!encoder.matches(usuarioLogin.getPassword(), usuario.getPassword())) {
                    response.setError(true);
                    response.setStatusCode(401);
                    response.setMessage("Usuario o contraseña incorrectos");
                    response.setData(null);
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                } else {
                    response.setError(false);
                    response.setStatusCode(200);
                    response.setMessage("Login exitoso");
                    Institucion institucion = institucionRepository.findByCorreo(usuarioLogin.getCorreo());
                    Map<String, Object> jsonMap = new HashMap<>();
                    jsonMap.put("estado", true);
                    jsonMap.put("idInstitucion", institucion.getIdInstitucion());
                    jsonMap.put("logo", institucion.getLogo());
                    jsonMap.put("nombre", institucion.getNombre());
                    jsonMap.put("correo", institucion.getCorreo());
                    jsonMap.put("password", institucion.getPassword());
                    jsonMap.put("cantidadCamillas", institucion.getCantidadCamillas());
                    jsonMap.put("cantidadDeSalas", institucion.getCantidadDeSalas());
                    jsonMap.put("cantidadDeIslas", institucion.getCantidadDeIslas());

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
        } else {
            response.setError(true);
            response.setStatusCode(401);
            response.setMessage("Usuario o contraseña incorrectos");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
