package mx.utez.simapi.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.repository.CamillasRepository;
import mx.utez.simapi.utils.CustomHandlerException;
import mx.utez.simapi.utils.CustomResponse;

@RestController
@RequestMapping("/api/movil")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MovilController {

    @Autowired
    CamillasRepository camillasRepository;

    @GetMapping("/validate/{numeroExpediente}/{turno}/{idEnfermera}")
    public ResponseEntity<CustomResponse<Object>> validate(@PathVariable String numeroExpediente,
            @PathVariable String turno, @PathVariable String idEnfermera) {
        CustomResponse<Object> response = new CustomResponse<>();
        try {
            long count = camillasRepository.countByNumeroExpedienteAndIdEnfermeraInTurno(numeroExpediente, turno,
                    idEnfermera);
            if (count > 0) {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("El paciente se encuentra en el turno");

                Map<String, Object> data = new HashMap<>();
                data.put("valid", true);

                response.setData(data);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("El paciente no se encuentra en el turno");
                response.setData(null);

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(500);
            response.setMessage(CustomHandlerException.handleException(e));
            response.setData(null);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
