package mx.utez.simapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.models.Camillas;
import mx.utez.simapi.repository.CamillasRepository;
import mx.utez.simapi.utils.CustomHandlerException;
import mx.utez.simapi.utils.CustomResponse;
import mx.utez.simapi.utils.UUIDGenerator;

@RestController
@RequestMapping("/camillas")
public class CamillasController {
    @Autowired
    private CamillasRepository camillasRepository;

    @PostMapping
    public ResponseEntity<CustomResponse<Camillas>> createCamilla(@RequestBody Camillas camilla) {
        CustomResponse<Camillas> response = new CustomResponse<Camillas>();
        try {
            if (camilla == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Camilla no creada, datos incompletos");
                response.setData(camilla);
            } else if (camilla.getIdEnfermera().isEmpty()
                    || camilla.getIdPaciente().isEmpty() || camilla.isEstado() == false) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Camilla no creada, datos incompletos");
                response.setData(camilla);
            } else if (!camilla.getIdCamilla().isEmpty()) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Camilla no creada, idCamilla no puede ser aplicado");
                response.setData(camilla);
            } else {
                camilla.setIdCamilla(UUIDGenerator.getId());
                camillasRepository.save(camilla);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Camilla creada correctamente");
                response.setData(camilla);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al crear camilla");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
