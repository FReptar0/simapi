package mx.utez.simapi.controllers.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.controllers.UsuariosController;
import mx.utez.simapi.models.JwtToken;
import mx.utez.simapi.models.UsuarioLogin;
import mx.utez.simapi.utils.CustomResponse;

@RestController
@RequestMapping("/api/auth/admin")
public class AdminAuthController {

    @Autowired
    UsuariosController usuariosController;

    @PostMapping("/login")
    public ResponseEntity<CustomResponse<JwtToken>> login(@Valid @RequestBody UsuarioLogin usuarioLogin) {
        // Response
        CustomResponse<JwtToken> response = new CustomResponse<>();
        try {
            JwtToken jwtToken = usuariosController.login(usuarioLogin);
            if (jwtToken != null) {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Login exitoso");
                response.setData(jwtToken);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setError(true);
                response.setStatusCode(401);
                response.setMessage("Usuario o contraseña incorrectos");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
