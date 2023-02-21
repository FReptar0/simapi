package mx.utez.simapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        System.out.println(camilla.toString());
        try {
            if (camilla.getIdEnfermera().isEmpty()
                    || camilla.getIdPaciente().isEmpty()) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Camilla no creada, datos incompletos");
                response.setData(camilla);
            } else if (camilla.getIdCamilla() == null) {
                camilla.setIdCamilla(UUIDGenerator.getId());
                System.out.println(camilla.getIdCamilla());
                camillasRepository.save(camilla);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Camilla creada correctamente");
                response.setData(camilla); // invertir con el de arriba
            } else if (!camilla.getIdCamilla().isEmpty()) {
                System.out.println("idCamilla no puede ser aplicado");
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Camilla no creada, idCamilla no puede ser aplicado");
                response.setData(camilla);
            } else {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Camilla no creada");
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

    @GetMapping
    public ResponseEntity<CustomResponse<List<Camillas>>> getCamillas() {
        CustomResponse<List<Camillas>> response = new CustomResponse<List<Camillas>>();
        try {
            List<Camillas> camillas = camillasRepository.findAll();
            if (camillas.isEmpty()) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("No hay camillas registradas");
                response.setData(null);
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
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al obtener camillas");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{idCamilla}")
    public ResponseEntity<CustomResponse<Camillas>> getCamillaById(@PathVariable String idCamilla) {
        CustomResponse<Camillas> response = new CustomResponse<Camillas>();
        try {
            Camillas camilla = camillasRepository.findById(idCamilla).orElse(null);
            if (camilla == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Camilla no encontrada");
                response.setData(null);
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
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al obtener camilla");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}