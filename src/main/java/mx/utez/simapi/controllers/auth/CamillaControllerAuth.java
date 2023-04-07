package mx.utez.simapi.controllers.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.models.Camillas;
import mx.utez.simapi.repository.CamillasRepository;
import mx.utez.simapi.utils.CustomHandlerException;
import mx.utez.simapi.utils.CustomResponse;

@RestController
@RequestMapping("/api/auth/camillas")
@CrossOrigin(origins = "*")
public class CamillaControllerAuth {
    @Autowired
    private CamillasRepository camillasRepository;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Camillas>>> getCamillas() {
        CustomResponse<List<Camillas>> response = new CustomResponse<List<Camillas>>();
        try {
            List<Camillas> camillas = camillasRepository.findAll();
            if (camillas.isEmpty()) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("No hay camillas");
                response.setData(camillas);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Camillas obtenidas correctamente");
                response.setData(camillas);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nCamillas no obtenidas");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{idCamillas}")
    public ResponseEntity<CustomResponse<Camillas>> getCamilla(@PathVariable String idCamillas) {
        CustomResponse<Camillas> response = new CustomResponse<Camillas>();
        try {
            Camillas camilla = camillasRepository.findById(idCamillas).orElse(null);
            if (camilla == null) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("Camilla no encontrada");
                response.setData(camilla);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Camilla obtenida correctamente");
                response.setData(camilla);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nCamilla no obtenida");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idCamillas}")
    public ResponseEntity<CustomResponse<Camillas>> updateCamilla(@PathVariable String idCamillas,
            @RequestBody Camillas camilla) {
        CustomResponse<Camillas> response = new CustomResponse<Camillas>();
        try {
            if (camilla == null) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("Camilla no actualizada, datos incompletos");
                response.setData(camilla);
            } else if ((camilla.getNombre() == null || camilla.getNumeroExpediente() == null
                    || camilla.getIdInstitucion() == null || camilla.getIdEnfermera() == null)
                    && camilla.getIdCamillas() == null) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("Camilla no actualizada, datos incompletos");
                response.setData(camilla);
            } else {
                if (camilla.getIdCamillas() != null) {
                    response.setError(true);
                    response.setStatusCode(400);
                    response.setMessage("Camilla no actualizada, idCamillas no debe ser enviado");
                } else {
                    camilla.setIdCamillas(idCamillas);
                    camillasRepository.save(camilla);
                    response.setError(false);
                    response.setStatusCode(200);
                    response.setMessage("Camilla actualizada correctamente");
                    response.setData(camilla);
                }
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nCamilla no actualizada");
            response.setData(camilla);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
