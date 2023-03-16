package mx.utez.simapi.controllers.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.utils.CustomResponse;
import mx.utez.simapi.models.Usuarios;
import mx.utez.simapi.models.filter.UsuarioFilter;
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
            response.setError(false);
            response.setStatusCode(200);
            response.setMessage("Datos de filtro de usuarios");
            Usuarios json = usuariosRepository.fbyIdUsuarios(idUsuario);
            UsuarioFilter usuarioFilter = new UsuarioFilter();
            usuarioFilter.setNombre(json.getNombre());
            usuarioFilter.setApellidos(json.getApellidos());
            usuarioFilter.setCorreo(json.getCorreo());
            response.setData(usuarioFilter);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(500);
            response.setMessage("Error al obtener datos de filtro de usuarios");
            response.setData(null);
            return ResponseEntity.status(500).body(response);
        }
    }
}
