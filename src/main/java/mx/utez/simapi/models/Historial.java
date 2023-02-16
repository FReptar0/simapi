package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document(collection = "historial")
public class Historial {
    @Id
    private Long idHistorial;
    private String fechaPeticion;
    private String horaDePeticion;
    private String fechaAtencion;
    private String horaDeAtencion;
    private String descripcion;
    private Long idPaciente;
    private Long idEnfermera;
    private Long idCamilla;
    private Long idIsla;
    private Long idSala;
}
