package mx.utez.simapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.models.Usuarios;
import mx.utez.simapi.repository.UsuariosRepository;
import mx.utez.simapi.utils.CustomHandlerException;
import mx.utez.simapi.utils.CustomResponse;
import mx.utez.simapi.utils.HashedPass;
import mx.utez.simapi.utils.UUIDGenerator;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {
    @Autowired
    private UsuariosRepository usuariosRepository;

    @PostMapping
    public ResponseEntity<CustomResponse<Usuarios>> save(@RequestBody Usuarios usuario) {
        CustomResponse<Usuarios> response = new CustomResponse<>();
        try {
            Usuarios usuarioDB = usuariosRepository.findByCorreo(usuario.getCorreo());
            if (usuarioDB != null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Usuario no creado, correo ya existente");
                response.setData(usuario);
            } else if ((usuario.getNombre() == null || usuario.getCorreo() == null || usuario.getPassword() == null)
                    && usuario.getIdUsuario() == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Usuario no creado, datos incompletos");
                response.setData(usuario);
            } else {
                usuario.setIdUsuario(UUIDGenerator.getId());
                usuario.setPassword(HashedPass.passwordEncoder().encode(usuario.getPassword()));
                usuariosRepository.save(usuario);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Usuario creado");
                response.setData(usuario);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(true);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al crear usuario");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<CustomResponse<List<Usuarios>>> getAll() {
        CustomResponse<List<Usuarios>> response = new CustomResponse<>();
        try {
            List<Usuarios> usuarios = usuariosRepository.findAll();
            response.setError(false);
            response.setStatusCode(200);
            response.setMessage("Usuarios obtenidos correctamente");
            response.setData(usuarios);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(true);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al obtener usuarios");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<CustomResponse<Usuarios>> getById(@PathVariable String idUsuario) {
        CustomResponse<Usuarios> response = new CustomResponse<>();
        try {
            Usuarios usuario = usuariosRepository.findById(idUsuario).orElse(null);
            if (usuario == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Usuario no encontrado");
                response.setData(usuario);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Usuario obtenido correctamente");
                response.setData(usuario);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(true);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al obtener usuario");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<CustomResponse<Usuarios>> update(@PathVariable String idUsuario, @RequestBody Usuarios usuario) {
        CustomResponse<Usuarios> response = new CustomResponse<>();
        try {
            Usuarios usuarioDB = usuariosRepository.findById(idUsuario).orElse(null);
            if (usuarioDB == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Usuario no actualizado, no encontrado");
                response.setData(usuario);
            } else if (usuario.getNombre() == null || usuario.getCorreo() == null || usuario.getPassword() == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Usuario no actualizado, datos incompletos");
                response.setData(usuario);
            } else {
                usuarioDB.setNombre(usuario.getNombre());
                usuarioDB.setApellidos(usuario.getApellidos());
                usuarioDB.setCorreo(usuario.getCorreo());
                usuarioDB.setPassword(HashedPass.passwordEncoder().encode(usuario.getPassword()));
                usuarioDB.setRol(usuario.getRol());
                usuarioDB.setIdInstitucion(usuario.getIdInstitucion());
                usuariosRepository.save(usuarioDB);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Usuario actualizado");
                response.setData(usuarioDB);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(true);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al actualizar usuario");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<CustomResponse<Usuarios>> delete(@PathVariable String idUsuario) {
        CustomResponse<Usuarios> response = new CustomResponse<>();
        try {
            Usuarios usuarioDB = usuariosRepository.findById(idUsuario).orElse(null);
            if (usuarioDB == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Usuario no eliminado, no encontrado");
                response.setData(usuarioDB);
            } else {
                usuariosRepository.delete(usuarioDB);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Usuario eliminado");
                response.setData(usuarioDB);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(true);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al eliminar usuario");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
