package mx.utez.simapi.controllers.filter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.utils.CustomResponse;
import mx.utez.simapi.models.Usuarios;
import mx.utez.simapi.repository.UsuariosRepository;

@RestController
@RequestMapping("/api/usuario/filter")
public class UsuariosFilterDataController {
    @Autowired
    private UsuariosRepository usuariosRepository;

    @GetMapping("/{idUsuario}")
    public ResponseEntity<CustomResponse<Object>> getUsuariosFilterData(@PathVariable String idUsuario) {
        CustomResponse<Object> response = new CustomResponse<Object>();
        try {
            Usuarios user = usuariosRepository.fbyIdUsuarios(idUsuario);
            if (user != null) {
                
                Map<String, Object> jsonMap = new HashMap<>();
                jsonMap.put("nombre", user.getNombre());
                jsonMap.put("apellidos", user.getApellidos());
                jsonMap.put("correo", user.getCorreo());

                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Datos de filtro de usuarios");
                response.setData(jsonMap);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setError(true);
                response.setStatusCode(401);
                response.setMessage("Usuario no encontrado");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(500);
            response.setMessage("Error al obtener datos de filtro de usuarios");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
