package mx.utez.simapi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.models.Camillas;
import mx.utez.simapi.repository.CamillasRepository;
import mx.utez.simapi.utils.CustomHandlerException;
import mx.utez.simapi.utils.CustomResponse;
import mx.utez.simapi.utils.UUIDGenerator;

@RestController
@RequestMapping("/api/camillas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CamillasController {
    @Autowired
    private CamillasRepository camillasRepository;

    @PostMapping
    public ResponseEntity<CustomResponse<Camillas>> createCamilla(@RequestBody Camillas camilla) {
        CustomResponse<Camillas> response = new CustomResponse<Camillas>();
        try {
            if ((camilla.getIdEnfermera() == null || camilla.getIdInstitucion() == null || camilla.getIdIsla() == 0
                    || camilla.getIdSala() == 0 || camilla.getNombre() != null
                    || camilla.getNumeroExpediente() != null || camilla.getIdBoton() == 0)
                    && (camilla.getIdCamillas() != null && camilla.isEstado() == true
                            && camilla.isEstadoAlarma() == true)) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("Camilla no creada, datos invalidos");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                camilla.setIdCamillas(UUIDGenerator.getId());
                camillasRepository.save(camilla);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Camilla creada correctamente");
                response.setData(camilla);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nCamilla no creada");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

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

    @GetMapping("/institucion/{idInstitucion}")
    public ResponseEntity<CustomResponse<List<Camillas>>> getCamillasByInstitucion(
            @PathVariable String idInstitucion) {
        CustomResponse<List<Camillas>> response = new CustomResponse<List<Camillas>>();
        try {
            List<Camillas> camillas = camillasRepository.findByIdInstitucion(idInstitucion);
            if (camillas == null) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("No hay camillas");
                response.setData(camillas);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Camillas obtenidas correctamente");
                response.setData(camillas);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(500);
            response.setMessage(CustomHandlerException.handleException(e) + "\nCamillas no obtenidas");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sala/{idSala}")
    public ResponseEntity<CustomResponse<List<Camillas>>> getCamillasBySala(@PathVariable int idSala) {
        CustomResponse<List<Camillas>> response = new CustomResponse<List<Camillas>>();
        try {
            List<Camillas> camillas = camillasRepository.findByIdSala(idSala);
            if (camillas == null) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("No hay camillas");
                response.setData(camillas);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Camillas obtenidas correctamente");
                response.setData(camillas);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(500);
            response.setMessage(CustomHandlerException.handleException(e) + "\nCamillas no obtenidas");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/isla/{idIsla}")
    public ResponseEntity<CustomResponse<List<Camillas>>> getCamillasByIsla(@PathVariable int idIsla) {
        CustomResponse<List<Camillas>> response = new CustomResponse<List<Camillas>>();
        try {
            List<Camillas> camillas = camillasRepository.findByIdIsla(idIsla);
            if (camillas == null) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("No hay camillas");
                response.setData(camillas);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Camillas obtenidas correctamente");
                response.setData(camillas);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(500);
            response.setMessage(CustomHandlerException.handleException(e) + "\nCamillas no obtenidas");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/enfermera/{idEnfermera}")
    public ResponseEntity<CustomResponse<List<Camillas>>> getCamillasByEnfermera(
            @PathVariable String idEnfermera) {
        CustomResponse<List<Camillas>> response = new CustomResponse<List<Camillas>>();
        try {
            List<Camillas> camillas = camillasRepository.findByIdEnfermera(idEnfermera);
            if (camillas == null) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("No hay camillas");
                response.setData(camillas);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Camillas obtenidas correctamente");
                response.setData(camillas);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(500);
            response.setMessage(CustomHandlerException.handleException(e) + "\nCamillas no obtenidas");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idCamillas}")
    public ResponseEntity<CustomResponse<Camillas>> updateCamilla(@PathVariable String idCamillas,
            @RequestBody Camillas camilla) {
        CustomResponse<Camillas> response = new CustomResponse<Camillas>();
        try {
            Camillas camillaToUpdate = camillasRepository.findById(idCamillas).orElse(null);
            if (camillaToUpdate == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Camilla no encontrada");
                response.setData(camilla);
                return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
            } else if (camilla.getIdEnfermera() == null || camilla.getIdInstitucion() == null
                    || camilla.getIdIsla() == 0
                    || camilla.getIdSala() == 0 || camilla.getNombre() == null
                    || camilla.getNumeroExpediente() == null || camilla.getIdBoton() == 0) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Camilla no actualizada, faltan datos");
                response.setData(camilla);
                return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
            } else {
                camillaToUpdate.setIdEnfermera(camilla.getIdEnfermera());
                camillaToUpdate.setIdInstitucion(camilla.getIdInstitucion());
                camillaToUpdate.setIdIsla(camilla.getIdIsla());
                camillaToUpdate.setIdSala(camilla.getIdSala());
                camillaToUpdate.setNombre(camilla.getNombre());
                camillaToUpdate.setNumeroExpediente(camilla.getNumeroExpediente());
                camillaToUpdate.setIdBoton(camilla.getIdBoton());
                camillaToUpdate.setEstado(camilla.isEstado());
                camillaToUpdate.setEstadoAlarma(camilla.isEstadoAlarma());
                camillasRepository.save(camillaToUpdate);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Camilla actualizada correctamente");
                response.setData(camillaToUpdate);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(500);
            response.setMessage(CustomHandlerException.handleException(e) + "\nCamilla no actualizada");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/alarma/{idCamillas}")
    public ResponseEntity<CustomResponse<Camillas>> updateEstadoCamilla(@PathVariable String idCamillas,
            @RequestBody Camillas camilla) {
        CustomResponse<Camillas> response = new CustomResponse<Camillas>();
        try {
            Camillas camillaToUpdate = camillasRepository.findById(idCamillas).orElse(null);
            if (camillaToUpdate == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Camilla no encontrada");
                response.setData(camilla);
                return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
            } else {
                camillaToUpdate.setEstadoAlarma(camilla.isEstadoAlarma());
                camillasRepository.save(camillaToUpdate);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Camilla actualizada correctamente");
                response.setData(camillaToUpdate);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nCamilla no actualizada");
            response.setData(camilla);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{idCamillas}")
    public ResponseEntity<CustomResponse<Camillas>> deleteCamilla(@PathVariable String idCamillas) {
        CustomResponse<Camillas> response = new CustomResponse<Camillas>();
        try {
            Camillas camilla = camillasRepository.findById(idCamillas).orElse(null);
            if (camilla == null) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("Camilla no encontrada");
                response.setData(camilla);
            } else {
                camillasRepository.delete(camilla);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Camilla eliminada correctamente");
                response.setData(camilla);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nCamilla no eliminada");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/map/sala/{idSala}/isla/{idIsla}/institucion/{idInstitucion}")
    public ResponseEntity<CustomResponse<List<Camillas>>> createCamillas(@PathVariable int idSala,
            @PathVariable int idIsla, @PathVariable String idInstitucion) {
        CustomResponse<List<Camillas>> response = new CustomResponse<List<Camillas>>();
        try {
            int cantidadCamillas = idSala * 10;
            int ctIslas = 1;
            int ctSala = 1;
            List<Camillas> camillas = new ArrayList<Camillas>();
            List<String> idEnfermera = new ArrayList<String>();
            idEnfermera.add("");
            for (int i = 0; i < cantidadCamillas; i++) {
                Camillas camilla = new Camillas();
                camilla.setIdCamillas(UUIDGenerator.getId());
                camilla.setIdEnfermera(idEnfermera);
                camilla.setIdInstitucion(idInstitucion);
                camilla.setIdIsla(ctIslas);
                camilla.setIdSala(ctSala);
                camilla.setNombre("");
                camilla.setNumeroExpediente("");
                camilla.setIdBoton(0);
                camilla.setEstado(false);
                camilla.setEstadoAlarma(false);
                camillas.add(camilla);
                ctIslas++;
                if (ctIslas > idIsla) {
                    ctIslas = 1;
                    ctSala++;
                }
                if (ctSala > idSala) {
                    ctSala = 1;
                }
            }

            camillasRepository.saveAll(camillas);
            response.setError(false);
            response.setStatusCode(200);
            response.setMessage("Camillas creadas correctamente");
            response.setData(camillas);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(500);
            response.setMessage(CustomHandlerException.handleException(e) + "\nCamillas no creadas");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
