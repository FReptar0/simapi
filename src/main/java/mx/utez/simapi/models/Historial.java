package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document(collection = "historial")
public class Historial {
    @Id
    private String idHistorial;
    private String fechaPeticion;
    private String horaDePeticion;
    private String fechaAtencion;
    private String horaDeAtencion;
    private String comentario;
    private String idPaciente;
    private String idEnfermera;
    private String idCamilla;
    private String idIsla;
    private String idSala;
}
