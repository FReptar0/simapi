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
import org.springframework.web.bind.annotation.RestController;
import mx.utez.simapi.models.Salas;
import mx.utez.simapi.repository.SalasRepository;
import mx.utez.simapi.utils.CustomHandlerException;
import mx.utez.simapi.utils.CustomResponse;
import mx.utez.simapi.utils.UUIDGenerator;

@RestController
@RequestMapping("/salas")

public class SalasController {
    @Autowired
    private SalasRepository salasRepository;
    @PostMapping
    public ResponseEntity<CustomResponse<Salas>> createSala(@RequestBody Salas sala){
        CustomResponse<Salas> response = new CustomResponse<Salas>();
        try{
            if(sala.getNumeroDeSala().isEmpty() || sala.getCantidadDeCamillas().isEmpty() || 
            sala.getIdJefeDeEnfermeria().isEmpty() || sala.getIdEnfermeraResponsable().isEmpty()){
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Verifica que los campos estén llenos");
                response.setData(sala);
            }else if(sala.getIdSala()== null){
                sala.setIdSala(UUIDGenerator.getId());
                salasRepository.save(sala);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Sala creada correctamente");
                response.setData(sala);
            }else if (sala.getIdSala() != null){
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Sala ya existe");
                response.setData(sala);
            }else {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Sala no creada");
                response.setData(sala);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nSala no creada");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<CustomResponse<List<Salas>>> getSalas(){
        CustomResponse<List<Salas>> response = new CustomResponse<List<Salas>>();
        try{
            List<Salas> sala = salasRepository.findAll();
            if(sala.isEmpty()){
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("No hay salas registradas");
                response.setData(null);
            }else{
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Pacientes obtenidos correctamente");
                response.setData(sala);
            }
            return new ResponseEntity<CustomResponse<List<Salas>>>(response, HttpStatus.OK);
        }catch (Exception e){
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nNo se pudo obtener las salas");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    
    }

    @GetMapping(value="/{idSala}")
    public ResponseEntity<CustomResponse<Salas>> getSalaById(@PathVariable String idSala){
        CustomResponse<Salas> response = new CustomResponse<Salas>();
        try{
            Salas sala = salasRepository.findById(idSala).orElse(null);
            if(sala == null){
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Sala no encontrada");
                response.setData(null);
            }else{
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Sala obtenida correctamente");
                response.setData(sala);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(Exception e){
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nNo se pudo obtener la sala");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value="/{idSala}")
    public ResponseEntity<CustomResponse<Salas>> updateSala(@PathVariable String idSala,
    @RequestBody Salas sala){
        CustomResponse<Salas> response = new CustomResponse<Salas>();
        try{
            Salas salaUpdate = salasRepository.findById(idSala).orElse(null);
            if(salaUpdate == null){
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Sala no encontrada");
                response.setData(null);
            }else{
                salaUpdate.setNumeroDeSala(sala.getNumeroDeSala());
                salaUpdate.setCantidadDeCamillas(sala.getCantidadDeCamillas());
                salaUpdate.setIdJefeDeEnfermeria(sala.getIdJefeDeEnfermeria());
                salaUpdate.setIdEnfermeraResponsable(sala.getIdEnfermeraResponsable());
                salasRepository.save(salaUpdate);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Sala actualizada correctamente");
                response.setData(salaUpdate);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nNo se pudo actualizar la sala");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @DeleteMapping(value="/{idSala}")
    public ResponseEntity<CustomResponse<Salas>> deleteSala(@PathVariable String idSala){
        CustomResponse <Salas> response = new CustomResponse<Salas>();
        try{
            Salas sala = salasRepository.findById(idSala).orElse(null);
            if( sala == null){
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Sala no encontrada");
                response.setData(null);
            }else{
                salasRepository.delete(sala);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Sala eliminada correctamente");
                response.setData(sala);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(Exception e){
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nNo se pudo eliminar la sala");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
