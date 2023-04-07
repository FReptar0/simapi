package mx.utez.simapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.models.JwtToken;
import mx.utez.simapi.models.UsuarioLogin;
import mx.utez.simapi.models.Usuarios;
import mx.utez.simapi.repository.UsuariosRepository;
import mx.utez.simapi.security.jwt.JwtProvider;
import mx.utez.simapi.utils.CustomHandlerException;
import mx.utez.simapi.utils.CustomResponse;
import mx.utez.simapi.utils.UUIDGenerator;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

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
            } else if ((usuario.getNombre() == null || usuario.getApellidos() == null || usuario.getCorreo() == null
                    || usuario.getPassword() == null
                    || usuario.getRol() == null)
                    && usuario.getIdUsuario() == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Usuario no creado, datos incompletos");
                response.setData(usuario);
            } else {
                if (usuario.getRol().equals("SA") || usuario.getRol().equals("E") || usuario.getRol().equals("A")) {
                    if (usuario.getRol() == "SA" && usuariosRepository.countSA() > 0) {
                        response.setError(true);
                        response.setStatusCode(400);
                        response.setMessage("Usuario no creado, rol SA ya existente");
                        response.setData(usuario);
                    } else {
                        usuario.setIdUsuario(UUIDGenerator.getId());
                        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                        usuarioDB = usuariosRepository.save(usuario);
                        response.setError(false);
                        response.setStatusCode(200);
                        response.setMessage("Usuario creado correctamente");
                        usuarioDB.setPassword(null);
                        response.setData(usuarioDB);
                    }
                } else {
                    response.setError(true);
                    response.setStatusCode(400);
                    response.setMessage("Usuario no creado, rol no valido");
                    response.setData(usuario);
                }
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
            for (Usuarios usuario : usuarios) {
                usuario.setPassword(null);
            }
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
                usuario.setPassword(null);
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

    @GetMapping("/institucion/{idInstitucion}")
    public ResponseEntity<CustomResponse<List<Usuarios>>> getByInstitucion(@PathVariable String idInstitucion) {
        CustomResponse<List<Usuarios>> response = new CustomResponse<>();
        try {
            List<Usuarios> usuarios = usuariosRepository.findByIdInstitucion(idInstitucion);
            response.setError(false);
            response.setStatusCode(200);
            response.setMessage("Usuarios obtenidos correctamente");
            for (Usuarios usuario : usuarios) {
                usuario.setPassword(null);
            }
            response.setData(usuarios);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(true);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al obtener usuarios");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<CustomResponse<Usuarios>> update(@PathVariable String idUsuario,
            @RequestBody Usuarios usuario) {
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
                if (usuario.getRol() == "SA" || usuario.getRol() == "E" || usuario.getRol() == "A") {
                    if (usuario.getRol() == "SA" && usuariosRepository.countSA() > 0) {
                        response.setError(true);
                        response.setStatusCode(400);
                        response.setMessage("Usuario no actualizado, rol SA ya existente");
                        response.setData(usuario);
                    } else {
                        usuarioDB.setNombre(usuario.getNombre());
                        usuarioDB.setCorreo(usuario.getCorreo());
                        usuarioDB.setPassword(passwordEncoder.encode(usuario.getPassword()));
                        usuarioDB.setRol(usuario.getRol());
                        usuarioDB = usuariosRepository.save(usuarioDB);
                        response.setError(false);
                        response.setStatusCode(200);
                        response.setMessage("Usuario actualizado correctamente");
                        usuarioDB.setPassword(null);
                        response.setData(usuarioDB);
                    }
                }
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
                usuarioDB.setPassword(null);
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

    public JwtToken login(UsuarioLogin usuarioLogin) {
        // Obtener usuario desde la base de datos usando correo
        String correo = usuarioLogin.getCorreo();
        Usuarios usuario = usuariosRepository.findByCorreo(correo);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        } else {
            // Comparar contraseñas hasheadas
            String passwordHasheada = usuario.getPassword();
            String passwordIngresada = usuarioLogin.getPassword();
            if (!passwordEncoder.matches(passwordIngresada, passwordHasheada)) {
                throw new BadCredentialsException("Credenciales inválidas");
            }

            // Hacer autenticación con Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(correo, passwordIngresada));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            return new JwtToken(jwt);
        }

    }
}
