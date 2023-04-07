package mx.utez.simapi.controllers;

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

import mx.utez.simapi.models.Colores;
import mx.utez.simapi.repository.ColoresRepository;
import mx.utez.simapi.utils.CustomHandlerException;
import mx.utez.simapi.utils.CustomResponse;
import mx.utez.simapi.utils.UUIDGenerator;

@RestController
@RequestMapping("/api/colores")
@CrossOrigin(origins = "*") //darle acceso a todos los dominios para que puedan interactuar con el api
public class ColoresControler {
    
    @Autowired
    private ColoresRepository coloresRepository;

   @PostMapping
    public ResponseEntity<CustomResponse<Colores>> createColores(@RequestBody Colores colores) {
        CustomResponse<Colores> response = new CustomResponse<Colores>();
        try {
            if (colores == null) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("Colores no creado, datos incompletos");
                response.setData(colores);
            } else if ((colores.getColorPrimario() != null || colores.getColorSecundario() != null
                    || colores.getColorTerciario() != null || colores.getIdInstitucion() != null)
                    && colores.getIdColores() == null) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("Colores no creado, datos incompletos");
                response.setData(colores);
            } else {
                colores.setIdColores(UUIDGenerator.getId());
                coloresRepository.save(colores);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Colores creado correctamente");
                response.setData(colores);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nColores no creado");
            response.setData(colores);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<CustomResponse<List<Colores>>> getColores() {
        CustomResponse<List<Colores>> response = new CustomResponse<List<Colores>>();
        try {
            List<Colores> colores = coloresRepository.findAll();
            if (colores.isEmpty()) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("No se encontraron colores");
                response.setData(colores);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Colores encontrados");
                response.setData(colores);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nNo se encontraron colores");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Colores>> getColoresById(@PathVariable String id) {
        CustomResponse<Colores> response = new CustomResponse<Colores>();
        try {
            Colores colores = coloresRepository.findById(id).orElse(null);
            if (colores == null) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("No se encontraron colores");
                response.setData(colores);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Colores encontrados");
                response.setData(colores);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + " \nNo se encontraron colores");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Colores>> updateColores(@PathVariable String id,
            @RequestBody Colores colores) {
        CustomResponse<Colores> response = new CustomResponse<Colores>();
        try {
            Colores coloresToUpdate = coloresRepository.findById(id).orElse(null);
            if (coloresToUpdate == null) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("No se encontraron colores");
                response.setData(colores);
            } else {
                coloresToUpdate.setColorPrimario(colores.getColorPrimario());
                coloresToUpdate.setColorSecundario(colores.getColorSecundario());
                coloresToUpdate.setColorTerciario(colores.getColorTerciario());
                coloresToUpdate.setIdInstitucion(colores.getIdInstitucion());
                //guardar los cambios del registro en la base de datos
                coloresRepository.save(coloresToUpdate);
                coloresToUpdate.setIdColores(id);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Colores actualizados");
                response.setData(coloresToUpdate);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nNo se encontraron colores");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Colores>> deleteColores(@PathVariable String id) {
        CustomResponse<Colores> response = new CustomResponse<Colores>();
        try {
            Colores colores = coloresRepository.findById(id).orElse(null);
            if (colores == null) {
                response.setError(true);
                response.setStatusCode(200);
                response.setMessage("No se encontraron colores");
                response.setData(colores);
            } else {
                coloresRepository.delete(colores);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Colores eliminados");
                response.setData(colores);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nNo se encontraron colores");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
