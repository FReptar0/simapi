package mx.utez.simapi.controllers.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.controllers.UsuariosController;
import mx.utez.simapi.models.Colores;
import mx.utez.simapi.models.Institucion;
import mx.utez.simapi.models.JwtToken;
import mx.utez.simapi.models.UsuarioLogin;
import mx.utez.simapi.models.Usuarios;
import mx.utez.simapi.repository.ColoresRepository;
import mx.utez.simapi.repository.InstitucionRepository;
import mx.utez.simapi.repository.UsuariosRepository;
import mx.utez.simapi.utils.CustomHandlerException;
import mx.utez.simapi.utils.CustomResponse;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuariosAuthController {

    @Autowired
    UsuariosController usuariosController;
    @Autowired
    UsuariosRepository usuariosRepository;
    @Autowired
    ColoresRepository coloresRepository;
    @Autowired
    InstitucionRepository institucionRepository;

    @PostMapping("/login")
    public ResponseEntity<CustomResponse<Object>> login(@Valid @RequestBody UsuarioLogin usuarioLogin) {
        // Response
        CustomResponse<Object> response = new CustomResponse<>();
        try {
            JwtToken jwtToken = usuariosController.login(usuarioLogin);
            if (jwtToken != null) {

                Usuarios usuario = usuariosRepository.findByCorreo(usuarioLogin.getCorreo());
                Institucion institucion = institucionRepository.findByIdInstitucion(usuario.getIdInstitucion());

                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Login exitoso");

                Map<String, Object> jsonMap = new HashMap<>();
                jsonMap.put("token", jwtToken.getToken());
                jsonMap.put("correo", usuarioLogin.getCorreo());
                jsonMap.put("nombre", usuario.getNombre());
                jsonMap.put("apellidos", usuario.getApellidos());
                jsonMap.put("idUsuario", usuario.getIdUsuario());
                jsonMap.put("rol", usuario.getRol());

                if (usuario.getRol().equals("SA")) {
                    Colores color = new Colores();
                    color.setColorPrimario("#A3B2CF");
                    color.setColorSecundario("#385273");
                    color.setColorTerciario("#00264D");

                    jsonMap.put("colores", color);

                    response.setData(jsonMap);
                } else {
                    Colores color = coloresRepository.findByIdInstitucion(usuario.getIdInstitucion());
                    jsonMap.put("logo", institucion.getLogo());
                    jsonMap.put("colores", color);
                    response.setData(jsonMap);
                }

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
            response.setMessage(CustomHandlerException.handleException(e));
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/institucion/{idInstitucion}")
    public ResponseEntity<CustomResponse<List<Usuarios>>> getByInstitucion(@PathVariable String idInstitucion) {
        CustomResponse<List<Usuarios>> response = new CustomResponse<>();
        try {
            List<Usuarios> usuarios = usuariosRepository.findByIdInstitucion(idInstitucion);
            if (usuarios == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Usuarios no encontrados");
                response.setData(usuarios);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Usuarios obtenidos correctamente");
                for (Usuarios usuario : usuarios) {
                    usuario.setPassword(null);
                }
                response.setData(usuarios);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(true);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al obtener usuarios");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
