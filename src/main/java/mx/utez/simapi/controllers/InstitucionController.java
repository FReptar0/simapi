package mx.utez.simapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.models.Colores;
import mx.utez.simapi.models.Institucion;
import mx.utez.simapi.repository.InstitucionRepository;
import mx.utez.simapi.utils.CustomHandlerException;
import mx.utez.simapi.utils.CustomResponse;
import mx.utez.simapi.utils.UUIDGenerator;

@RestController
@RequestMapping("/api/institucion")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InstitucionController {
    @Autowired
    private InstitucionRepository institucionRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<CustomResponse<Institucion>> createInstitucion(@RequestBody Institucion institucion) {
        CustomResponse<Institucion> response = new CustomResponse<Institucion>();
        try {
            if (institucion == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Institucion no creada, datos incompletos");
                response.setData(institucion);
            } else if ((institucion.getNombre() == null || institucion.getCorreo() == null
                    || institucion.getPassword() == null || institucion.getLogo() == null
                    || institucion.getCantidadCamillas() == 0 || institucion.getCantidadDeSalas() == 0
                    || institucion.getCantidadDeIslas() == 0)
                    && institucion.getIdInstitucion() == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Institucion no creada, datos incompletos");
                response.setData(institucion);
            } else {
                if (institucionRepository.existsByNombre(institucion.getNombre())) {
                    response.setError(true);
                    response.setStatusCode(400);
                    response.setMessage("Institucion no creada, nombre ya existente");
                    response.setData(institucion);
                } else if (institucionRepository.existsByCorreo(institucion.getCorreo())) {
                    response.setError(true);
                    response.setStatusCode(400);
                    response.setMessage("Institucion no creada, correo ya existente");
                    response.setData(institucion);
                } else {
                    institucion.setIdInstitucion(UUIDGenerator.getId());
                    String password = institucion.getPassword();
                    institucion.setPassword(passwordEncoder.encode(institucion.getPassword()));
                    Boolean passwordMatch = passwordEncoder.matches(password, institucion.getPassword());
                    System.out.println("Password match: " + passwordMatch);
                    //ColoresControler coloresControler = new ColoresControler();
                    //ResponseEntity<CustomResponse<Colores>> responseColores = coloresControler.createColores(new Colores(institucion.getIdInstitucion(), "#A3B2CF", "#385273", "#00264D"));
                    //System.out.println("MENSAJE COLORES: " + responseColores.getBody().getMessage());
                    institucionRepository.save(institucion);
                    response.setError(false);
                    response.setStatusCode(200);
                    response.setMessage("Institucion creada");
                    response.setData(institucion);
                }
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nInstitucion no creada");
            response.setData(institucion);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<CustomResponse<List<Institucion>>> getAllInstituciones() {
        CustomResponse<List<Institucion>> response = new CustomResponse<List<Institucion>>();
        try {
            List<Institucion> instituciones = institucionRepository.findAll();
            if (instituciones.isEmpty()) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("No se encontraron instituciones");
                response.setData(instituciones);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Instituciones encontradas");
                response.setData(instituciones);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nNo se encontraron instituciones");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{idInstitucion}")
    public ResponseEntity<CustomResponse<Institucion>> getInstitucionById(@PathVariable String idInstitucion) {
        CustomResponse<Institucion> response = new CustomResponse<Institucion>();
        try {
            Institucion institucion = institucionRepository.findById(idInstitucion).orElse(null);
            if (institucion == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Institucion no encontrada");
                response.setData(institucion);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Institucion encontrada");
                response.setData(institucion);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nInstitucion no encontrada");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idInstitucion}")
    public ResponseEntity<CustomResponse<Institucion>> updateInstitucion(@PathVariable String idInstitucion,
            @RequestBody Institucion institucion) {
        CustomResponse<Institucion> response = new CustomResponse<Institucion>();
        try {
            Institucion institucionDB = institucionRepository.findById(idInstitucion).orElse(null);
            if (institucionDB == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Institucion no encontrada");
                response.setData(institucion);
            } else if ((institucion.getNombre() != null || institucion.getCorreo() != null
                    || institucion.getPassword() != null || institucion.getLogo() != null
                    || institucion.getCantidadCamillas() != 0 || institucion.getCantidadDeSalas() != 0
                    || institucion.getCantidadDeIslas() != 0)
                    && idInstitucion != null) {
                institucionDB.setNombre(institucion.getNombre());
                institucionDB.setCorreo(institucion.getCorreo());
                institucionDB.setPassword(passwordEncoder.encode(institucion.getPassword()));
                institucionDB.setLogo(institucion.getLogo());
                institucionDB.setCantidadCamillas(institucion.getCantidadCamillas());
                institucionDB.setCantidadDeSalas(institucion.getCantidadDeSalas());
                institucionDB.setCantidadDeIslas(institucion.getCantidadDeIslas());
                institucionDB.setCantidadCamillas(institucion.getCantidadCamillas());
                institucionRepository.save(institucionDB);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Institucion actualizada correctamente");
                response.setData(institucionDB);
            } else {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Institucion no actualizada, verifique los datos");
                response.setData(institucion);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nInstitucion no actualizada");
            response.setData(institucion);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idInstitucion}")
    public ResponseEntity<CustomResponse<Institucion>> deleteInstitucion(@PathVariable String idInstitucion) {
        CustomResponse<Institucion> response = new CustomResponse<Institucion>();
        try {
            Institucion institucion = institucionRepository.findById(idInstitucion).orElse(null);
            if (institucion == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Institucion no encontrada");
                response.setData(institucion);
            } else {
                institucionRepository.delete(institucion);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Institucion eliminada correctamente");
                response.setData(institucion);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nInstitucion no eliminada");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
