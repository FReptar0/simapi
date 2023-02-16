package mx.utez.simapi.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.models.Administradores;
import mx.utez.simapi.repository.AdministradoresRepository;
import mx.utez.simapi.utils.CustomResponse;
import mx.utez.simapi.utils.UUIDGenerator;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    @Autowired
    private AdministradoresRepository administradoresRepository;

    @PostMapping
    public ResponseEntity<CustomResponse> createAdministrador(@RequestBody Administradores admin) {
        CustomResponse response = new CustomResponse();
        // buscar si existe el administrador
        Administradores adminToCreate = administradoresRepository.findByCorreo(admin.getCorreo());
        try {
            if (admin.getApellidos().isEmpty() || admin.getNombre().isEmpty() || admin.getCorreo().isEmpty()
                    || admin.getContrasena().isEmpty() || admin.getRol().isEmpty()) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Verifica que todos los campos esten llenos");
                response.setData(admin);
            } else if (adminToCreate != null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("El administrador ya existe");
                response.setData(adminToCreate);
            } else {
                admin.setIdAdministrador(UUIDGenerator.getId());
                System.out.println(admin.getIdAdministrador());
                administradoresRepository.save(admin);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Administrador creado correctamente");
                response.setData(admin);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage("Error al crear administrador");
            response.setData(e.getMessage());
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<CustomResponse> getAdministradores() {
        CustomResponse response = new CustomResponse();
        try {
            if (administradoresRepository.findAll().isEmpty()) {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Error al obtener administradores");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Administradores obtenidos exitosamente");
                response.setData(administradoresRepository.findAll());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage("Error al obtener administradores");
            response.setData(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{idAdministrador}")
    public ResponseEntity<CustomResponse> getAdmin(@PathVariable String idAdministrador) {
        CustomResponse response = new CustomResponse();
        try {

            if (administradoresRepository.findById(idAdministrador).isEmpty()) {
                response.setError(false);
                response.setStatusCode(400);
                response.setMessage("Administrador no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Administrador obtenido correctamente");
                response.setData(administradoresRepository.findById(idAdministrador));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage("Error al obtener administradores");
            response.setData(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{idAdministrador}")
    public ResponseEntity<CustomResponse> updateAdmin(@PathVariable String idAdministrador,
            @RequestBody Administradores admin) {
        CustomResponse response = new CustomResponse();
        Administradores adminToUpdate = administradoresRepository.findById(idAdministrador).get();
        adminToUpdate.setIdAdministrador(idAdministrador);
        admin.setIdAdministrador(idAdministrador);
        try {
            if (admin.getApellidos().isEmpty() || admin.getNombre().isEmpty() || admin.getCorreo().isEmpty()
                    || admin.getContrasena().isEmpty() || admin.getRol().isEmpty()) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Verifica que todos los campos esten llenos");
                response.setData(admin);
            } else if (adminToUpdate == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Administrador no encontrado");
                response.setData(admin);
            } else {
                adminToUpdate.setApellidos(admin.getApellidos());
                adminToUpdate.setNombre(admin.getNombre());
                adminToUpdate.setCorreo(admin.getCorreo());
                adminToUpdate.setContrasena(admin.getContrasena());
                adminToUpdate.setRol(admin.getRol());
                administradoresRepository.save(adminToUpdate);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Administrador actualizado correctamente");
                response.setData(admin);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage("Error al actualizar administrador");
            response.setData(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{idAdministrador}")
    public ResponseEntity<CustomResponse> deleteAdministrador(@PathVariable String idAdministrador) {
        CustomResponse response = new CustomResponse();
        Administradores adminToDelete = administradoresRepository.findById(idAdministrador).get();
        try {
            if (adminToDelete == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Administrador no encontrado");
                response.setData(adminToDelete);
            } else {
                administradoresRepository.delete(adminToDelete);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Administrador eliminado correctamente");
                response.setData(adminToDelete);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage("Error al eliminar administrador");
            response.setData(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
