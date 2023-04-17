package mx.utez.simapi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.utez.simapi.models.Camillas;
import mx.utez.simapi.models.Historial;
import mx.utez.simapi.models.Message;
import mx.utez.simapi.models.Tiempo;
import mx.utez.simapi.models.UsuarioLogin;
import mx.utez.simapi.models.Usuarios;
import mx.utez.simapi.repository.CamillasRepository;
import mx.utez.simapi.repository.HistorialRepository;
import mx.utez.simapi.repository.UsuariosRepository;
import mx.utez.simapi.utils.CustomHandlerException;
import mx.utez.simapi.utils.CustomResponse;
import mx.utez.simapi.utils.RequestURL;
import mx.utez.simapi.utils.Time;
import mx.utez.simapi.utils.UUIDGenerator;

@RestController
@RequestMapping("/api/alarma")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AlarmaController {

    @Autowired
    private CamillasRepository camillasRepository;

    @Autowired
    private HistorialRepository historialRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/activate/{idBoton}/idInstitucion/{idInstitucion}")
    public ResponseEntity<CustomResponse<Object>> activateAlarm(@PathVariable int idBoton,
            @PathVariable String idInstitucion) {
        CustomResponse<Object> response = new CustomResponse<>();
        try {
            Camillas camilla = camillasRepository.findByIdBotonAndIdInstitucion(idBoton, idInstitucion);
            Tiempo tiempo = Time.getTime();

            if (camilla == null) {
                response.setError(true);
                response.setMessage("No se encontró la camilla");
                response.setData(null);
                response.setStatusCode(404);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else if (camilla.isEstado() == false || camilla.isEstadoAlarma() == true) {
                response.setError(true);
                response.setMessage("La camilla no está activa o ya tiene una alarma activada");
                response.setData(null);
                response.setStatusCode(400);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                Historial historial = new Historial(UUIDGenerator.getId(), tiempo.getFecha(), tiempo.getHora(), "", "",
                        camilla.getIdInstitucion(), camilla.getNumeroExpediente(), "", camilla.getIdCamillas(),
                        camilla.getNombre(), "");
                historialRepository.save(historial);
                camilla.setEstadoAlarma(true);
                camillasRepository.save(camilla);

                response.setError(false);
                response.setMessage("Alarma activada");

                Map<String, Object> data = new HashMap<>();
                data.put("historial", historial);
                data.put("camilla", camilla);

                response.setData(data);
                response.setStatusCode(200);

                Message message = new Message();
                message.setTo("all");
                message.setText("El paciente " + camilla.getNombre() + " ha activado la alarma");
                String nombre = camilla.getNombre().split(" ")[0];

                RequestURL.request(1, camilla.getNumeroExpediente(), nombre);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setError(true);
            response.setMessage(CustomHandlerException.handleException(e));
            response.setData(null);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deactivate/{idCamilla}")
    public ResponseEntity<CustomResponse<Object>> deactivateAlarm(@PathVariable String idCamilla,
            @RequestBody UsuarioLogin usuarioLogin) {
        CustomResponse<Object> response = new CustomResponse<>();
        try {
            Usuarios usuario = usuariosRepository.findByCorreo(usuarioLogin.getCorreo());
            Camillas camilla = camillasRepository.findById(idCamilla).orElse(null);
            if (usuario == null) {
                response.setError(true);
                response.setMessage("No se encontró el usuario");
                response.setData(null);
                response.setStatusCode(404);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else if (!passwordEncoder.matches(usuarioLogin.getPassword(), usuario.getPassword())) {
                response.setError(true);
                response.setMessage("Contraseña incorrecta");
                response.setData(null);
                response.setStatusCode(400);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else if (camilla == null) {
                response.setError(true);
                response.setMessage("No se encontró la camilla");
                response.setData(null);
                response.setStatusCode(404);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
                List<Historial> historiales = historialRepository.findByIdInstitucion(camilla.getIdInstitucion());
                Historial historial = historiales.get(historiales.size() - 1);
                if (historial == null) {
                    response.setError(true);
                    response.setMessage("No se encontró el historial");
                    response.setData(null);
                    response.setStatusCode(404);
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                } else {
                    historial.setFechaAtencion(Time.getTime().getFecha());
                    historial.setHoraDeAtencion(Time.getTime().getHora());
                    historial.setIdEnfermera(usuario.getIdUsuario());
                    historial.setNombreEnfermera(usuario.getNombre() + " " + usuario.getApellidos());
                    historialRepository.save(historial);
                    camilla.setEstadoAlarma(false);
                    camillasRepository.save(camilla);

                    response.setError(false);
                    response.setMessage("Alarma desactivada");

                    Map<String, Object> data = new HashMap<>();
                    data.put("historial", historial);
                    data.put("camilla", camilla);

                    response.setData(data);
                    response.setStatusCode(200);

                    // Obtener solo el nombre eliminar lo deamas despues del espacio
                    String nombre = camilla.getNombre().split(" ")[0];

                    RequestURL.request(2, usuario.getIdUsuario(), nombre);

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            response.setError(true);
            response.setMessage(CustomHandlerException.handleException(e));
            response.setData(null);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/deactivate/camilla/{idCamilla}/enfermera/{idEnfermera}")
    public ResponseEntity<CustomResponse<Object>> deactivateAlarm(@PathVariable String idCamilla,
            @PathVariable String idEnfermera) {
        CustomResponse<Object> response = new CustomResponse<>();
        try {
            Camillas camilla = camillasRepository.findById(idCamilla).orElse(null);
            if (camilla == null) {
                response.setError(true);
                response.setMessage("No se encontró la camilla");
                response.setData(null);
                response.setStatusCode(404);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
                List<Historial> historiales = historialRepository.findByIdInstitucion(camilla.getIdInstitucion());
                Historial historial = historiales.get(historiales.size() - 1);
                if (historial == null) {
                    response.setError(true);
                    response.setMessage("No se encontró el historial");
                    response.setData(null);
                    response.setStatusCode(404);
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                } else {
                    System.out.println(historial.toString());
                    historial.setFechaAtencion(Time.getTime().getFecha());
                    historial.setHoraDeAtencion(Time.getTime().getHora());
                    historial.setIdEnfermera(idEnfermera);
                    Usuarios usuario = usuariosRepository.findById(idEnfermera).orElse(null);
                    historial.setNombreEnfermera(usuario.getNombre() + " " + usuario.getApellidos());
                    historialRepository.save(historial);
                    camilla.setEstadoAlarma(false);
                    camillasRepository.save(camilla);

                    response.setError(false);
                    response.setMessage("Alarma desactivada");

                    Map<String, Object> data = new HashMap<>();
                    data.put("historial", historial);
                    data.put("camilla", camilla);

                    response.setData(data);
                    response.setStatusCode(200);

                    // Obtener solo el nombre elimina todo despues del primer espacio
                    String nombre = camilla.getNombre().split(" ")[0];

                    RequestURL.request(2, idEnfermera, nombre);

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            response.setError(true);
            response.setMessage(CustomHandlerException.handleException(e));
            response.setData(null);
            response.setStatusCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
