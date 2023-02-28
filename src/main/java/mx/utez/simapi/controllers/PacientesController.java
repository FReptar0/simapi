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

import mx.utez.simapi.models.Pacientes;
import mx.utez.simapi.repository.PacientesRepository;
import mx.utez.simapi.utils.CustomHandlerException;
import mx.utez.simapi.utils.CustomResponse;
import mx.utez.simapi.utils.UUIDGenerator;

@RestController
@RequestMapping("/pacientes")
public class PacientesController {
    @Autowired
    private PacientesRepository pacientesRepository;

    /**
     * @param patient
     * @return
     */
    @PostMapping
    public ResponseEntity<CustomResponse<Pacientes>> createPatient(@RequestBody Pacientes patient) {
        CustomResponse<Pacientes> response = new CustomResponse<Pacientes>();
        try {
            if (patient.getNombre().isEmpty() || patient.getApellidos().isEmpty()
                    || patient.getDescripcion().isEmpty()) {
                response.setError(true);// error = true
                response.setStatusCode(400);
                response.setMessage("Verifica que los campos estén llenos");
                response.setData(patient);
            } else if (!patient.getIdPaciente().isEmpty()) {
                System.out.println("idPaciente no puede ser aplicado");
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Paciente no creado, idPaciente no puede ser aplicado");
                response.setData(patient);
            } else if (patient.getIdPaciente() == null) {
                patient.setIdPaciente(UUIDGenerator.getId());
                pacientesRepository.save(patient);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Paciente creado correctamente");
                response.setData(patient);
            } else {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Paciente no creado");
                response.setData(patient);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {// error = true
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al crear paciente");
            response.setData(null);
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<CustomResponse<List<Pacientes>>> getPacientes() {
        CustomResponse<List<Pacientes>> response = new CustomResponse<List<Pacientes>>();
        try {
            List<Pacientes> paciente = pacientesRepository.findAll();// findAll() es un metodo de MongoRepository
            if (paciente.isEmpty()) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("No hay pacientes registrados");
                response.setData(null);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setData(paciente);
                response.setMessage("Pacientes  obtenidos correctamente");
            }
            return new ResponseEntity<CustomResponse<List<Pacientes>>>(response, HttpStatus.OK);// HttpStatus.OK = 200
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setData(null);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al obtener pacientes");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);// HttpStatus.INTERNAL_SERVER_ERROR
                                                                                    // = 500
        }
    }

    @GetMapping(value = "/{idPaciente}")
    public ResponseEntity<CustomResponse<Pacientes>> getPacienteById(@PathVariable String idPaciente) {
        CustomResponse<Pacientes> response = new CustomResponse<Pacientes>();
        try {
            Pacientes paciente = pacientesRepository.findById(idPaciente).orElse(null);// Para obtener un paciente por
                                                                                       // su id
            if (paciente == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Paciente no encontrado");
                response.setData(null);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setData(paciente);
                response.setMessage("Paciente obtenido correctamente");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setData(null);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al obtener paciente");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping(value = "/{idPaciente}")
    public ResponseEntity<CustomResponse<Pacientes>> updatePaciente(@PathVariable String idPaciente,
            @RequestBody Pacientes paciente) {
        CustomResponse<Pacientes> response = new CustomResponse<Pacientes>();
        try {
            Pacientes pacienteUpdate = pacientesRepository.findById(idPaciente).orElse(null);
            if (pacienteUpdate == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage(idPaciente + " no encontrado");
                response.setData(null);
            } else {
                pacienteUpdate.setNombre(paciente.getNombre());
                pacienteUpdate.setApellidos(paciente.getApellidos());
                pacienteUpdate.setDescripcion(paciente.getApellidos());
                pacientesRepository.save(pacienteUpdate);
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Paciente actualizado correctamente");
                response.setData(pacienteUpdate);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al actualizar paciente");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE
    @DeleteMapping(value = "/{idPaciente}")
    public ResponseEntity<CustomResponse<Pacientes>> deletePaciente(@PathVariable String idPaciente) {
        CustomResponse<Pacientes> response = new CustomResponse<Pacientes>();
        try {
            Pacientes paciente = pacientesRepository.findById(idPaciente).orElse(null);
            if (paciente == null) {
                response.setError(true);
                response.setStatusCode(400);
                response.setMessage("Paciente no encontrado");
                response.setData(null);
            } else {
                response.setError(false);
                response.setStatusCode(200);
                response.setMessage("Paciente eliminado correctamente");
                response.setData(null);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setError(true);
            response.setStatusCode(400);
            response.setMessage(CustomHandlerException.handleException(e) + "\nError al eliminar paciente");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}