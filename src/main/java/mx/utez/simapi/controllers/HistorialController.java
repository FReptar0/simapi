package mx.utez.simapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.models.Historial;
import mx.utez.simapi.repository.HistorialRepository;
import mx.utez.simapi.utils.CustomHandlerException;
import mx.utez.simapi.utils.CustomResponse;
import mx.utez.simapi.utils.UUIDGenerator;

@RestController
@RequestMapping("/historial")
public class HistorialController {

    @Autowired
    private HistorialRepository historialRepository;

    @PostMapping
    public ResponseEntity<CustomResponse<Historial>> createHistorial(@RequestBody Historial historial) {
        CustomResponse<Historial> response = new CustomResponse<Historial>();
        try {
            if (historial == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Historial no creado, datos incompletos");
                response.setData(historial);
            } else if ((historial.getFechaPeticion() == null || historial.getHoraDePeticion() == null
                    || historial.getFechaAtencion() == null || historial.getHoraDeAtencion() == null
                    || historial.getFechaAtencion() == null || historial.getIdPaciente() == null
                    || historial.getIdEnfermera() == null || historial.getIdCamilla() == null)
                    && historial.getIdHistorial() == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Historial no creado, datos incompletos");
                response.setData(historial);
            } else {
                historial.setIdHistorial(UUIDGenerator.getId());
                historialRepository.save(historial);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Historial creado correctamente");
                response.setData(historial);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nHistorial no creado");
            response.setData(historial);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<CustomResponse<List<Historial>>> getHistorial() {
        CustomResponse<List<Historial>> response = new CustomResponse<List<Historial>>();
        try {
            List<Historial> historial = historialRepository.findAll();
            response.setError(false);
            response.setStatusCode(200);
            response.setMessage("Historial obtenido correctamente");
            response.setData(historial);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nHistorial no obtenido");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{idHistorial}")
    public ResponseEntity<CustomResponse<Historial>> getHistorialById(@PathVariable String idHistorial) {
        CustomResponse<Historial> response = new CustomResponse<Historial>();
        try {
            Historial historial = historialRepository.findById(idHistorial).get();
            response.setError(false);
            response.setStatusCode(200);
            response.setMessage("Historial obtenido correctamente");
            response.setData(historial);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nHistorial no obtenido");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idHistorial}")
    public ResponseEntity<CustomResponse<Historial>> updateHistorial(@RequestBody Historial historial) {
        CustomResponse<Historial> response = new CustomResponse<Historial>();
        Historial historialToUpdate = historialRepository.findById(historial.getIdHistorial()).orElse(null);
        try {
            if (historialToUpdate != null) {
                historialToUpdate.setIdHistorial(historial.getIdHistorial());
                historialToUpdate.setFechaPeticion(historial.getFechaPeticion());
                historialToUpdate.setHoraDePeticion(historial.getHoraDePeticion());
                historialToUpdate.setFechaAtencion(historial.getFechaAtencion());
                historialToUpdate.setHoraDeAtencion(historial.getHoraDeAtencion());
                historialToUpdate.setComentario(historial.getComentario());
                historialToUpdate.setIdPaciente(historial.getIdPaciente());
                historialToUpdate.setIdEnfermera(historial.getIdEnfermera());
                historialToUpdate.setIdCamilla(historial.getIdCamilla());
                historialRepository.save(historialToUpdate);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Historial actualizado correctamente");
                response.setData(historialToUpdate);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Historial no actualizado");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nHistorial no actualizado");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}