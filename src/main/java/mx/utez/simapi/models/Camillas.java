package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document(collection = "camillas")
public class Camillas {
    @Id
    private String idCamilla;
    private String nombre;
    private String descripcion;
    private boolean estado;
    private Long idPaciente;
    private Long idIsla;
    private Long idSala;
    private Long idEnfermera;
}
