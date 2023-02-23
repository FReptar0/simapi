package mx.utez.simapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.models.Islas;
import mx.utez.simapi.repository.IslasRepository;
import mx.utez.simapi.utils.CustomResponse;
import mx.utez.simapi.utils.UUIDGenerator;
import mx.utez.simapi.utils.CustomHandlerException;

@RestController
@RequestMapping("/islas")
public class IslasController {

    @Autowired
    private IslasRepository islasRepository;

    @PostMapping
    public ResponseEntity<CustomResponse<Islas>> createIsla (@RequestBody Islas isla){
        CustomResponse<Islas> response = new CustomResponse<>();
        //Buscar si existe la isla
        Islas islaToCreate = islasRepository.findByNumeroDeIsla(isla.getNumeroDeIsla());
        try{
            if (isla.getNumeroDeIsla().isEmpty() || isla.getIdEnfermeraResponsable().isEmpty() || isla.getIdJefeDeEnfermeria().isEmpty()
            || isla.getIdSala().isEmpty()) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Verifica que todos los campos esten llenos");
                response.setData(isla);
            } else if(islaToCreate != null){
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("La isla ya existe");
                response.setData(isla);
            } else {
                isla.setIdIsla(UUIDGenerator.getId());
                islasRepository.save(isla);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("La isla creada correctamente");
                response.setData(isla);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al crear administrador");
            response.setData(null);
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<CustomResponse<List<Islas>>> getIslas(){
        CustomResponse<List<Islas>> response = new CustomResponse<>();
        try{
            List<Islas> islas = islasRepository.findAll();
            if (islas.isEmpty()) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("No hay islas registradas");
                response.setData(null);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Islas obtenidas correctamente");
                response.setData(islas);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al obtener islas");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{idIsla}")
    public ResponseEntity<CustomResponse<Islas>> getIslaById(@PathVariable String idIsla){
        CustomResponse<Islas> response = new CustomResponse<>();
        try{
            Islas isla = islasRepository.findById(idIsla).orElse(null);
            if(isla == null){
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Isla no encontrada");
                response.setData(null);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Isla obtenida correctamente");
                response.setData(isla);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al obtener isla");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{idIsla}")
    public ResponseEntity<CustomResponse<Islas>> updateIsla(@PathVariable String idIsla,
            @RequestBody Islas isla){
        CustomResponse<Islas> response = new CustomResponse<>();
        try{
            Islas islaToUpdate = islasRepository.findById(idIsla).orElse(null);
            if(islaToUpdate == null){
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Isla no encontrada");
                response.setData(null);
            } else {
                //String numeroDeIsla = isla.getNumeroDeIsla();
                Islas islaTemp =  islasRepository.findByNumeroDeIsla(isla.getNumeroDeIsla());
                //System.out.println(islaTemp);
                if(islaTemp != null){
                    response.setError(true);
                    response.setStatusCode(400);
                    response.setMessage("El numero de isla ya existe");
                    response.setData(null);
                } else {  
                    islaToUpdate.setNumeroDeIsla(isla.getNumeroDeIsla());
                    islaToUpdate.setIdEnfermeraResponsable(isla.getIdEnfermeraResponsable());
                    islaToUpdate.setIdJefeDeEnfermeria(isla.getIdJefeDeEnfermeria());
                    islaToUpdate.setIdSala(isla.getIdSala());
                    islaToUpdate.setEstado(isla.isEstado());
                    islasRepository.save(islaToUpdate);
                    response.setError(false);
                    response.setStatusCode(200);
                    response.setMessage("Isla actualizada correctamente");
                    response.setData(islaToUpdate);
                }
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al actualizar isla");
            //response.setMessage(""+e);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{idIsla}")
    public ResponseEntity<CustomResponse<Islas>> deleteIsla(@PathVariable String idIsla){
        CustomResponse<Islas> response = new CustomResponse<>();
        try{
            Islas isla = islasRepository.findById(idIsla).orElse(null);
            if(isla == null){
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Isla no encontrada");
                response.setData(null);
            } else {
                islasRepository.delete(isla);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Isla eliminada correctamente");
                response.setData(isla);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al eliminar isla");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
