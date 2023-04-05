package mx.utez.simapi.controllers.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.models.UsuarioLogin;
import mx.utez.simapi.models.Usuarios;
import mx.utez.simapi.repository.UsuariosRepository;
import mx.utez.simapi.utils.CustomResponse;

@RestController
@RequestMapping("/api/auth/institucion")
public class InstitucionAuthController {
    @Autowired
    UsuariosRepository usuariosRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<CustomResponse<Object>> login(@RequestBody UsuarioLogin usuarioLogin) {
        Usuarios usuario = usuariosRepository.findByCorreo(usuarioLogin.getCorreo());
        CustomResponse<Object> response = new CustomResponse<>();
        if (usuario != null) {
            try {
                usuarioLogin.setCorreo(usuario.getCorreo());
                usuarioLogin.setPassword(usuario.getPassword());
                if (passwordEncoder.matches(usuarioLogin.getPassword(), usuario.getPassword())) {
                    response.setError(false);
                    response.setStatusCode(200);
                    response.setMessage("Login exitoso");

                    Map<String, Object> jsonMap = new HashMap<>();
                    jsonMap.put("estado", true);

                    response.setData(jsonMap);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.setError(true);
                    response.setStatusCode(401);
                    response.setMessage("Usuario o contraseña incorrectos");
                    response.setData(null);
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
