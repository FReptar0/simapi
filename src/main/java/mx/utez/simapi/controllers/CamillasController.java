package mx.utez.simapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.models.Camillas;
import mx.utez.simapi.repository.CamillasRepository;
import mx.utez.simapi.utils.CustomResponse;

@RestController
@RequestMapping("/camillas")
public class CamillasController {
    @Autowired
    private CamillasRepository camillasRepository;

    @PostMapping
    public ResponseEntity<CustomResponse> createCamilla(@RequestBody Camillas camilla) {
        CustomResponse response = new CustomResponse();
        try {
            if (camilla != null) {
                camillasRepository.save(camilla);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Camilla creada correctamente");
                response.setData(camilla);
            } else if (!camilla.getIdCamilla().isEmpty()) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Camilla no creada, idCamilla no puede ser aplicado");
                response.setData(null);
            } else {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Camilla no creada, datos incompletos");
                response.setData(camilla);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
}
