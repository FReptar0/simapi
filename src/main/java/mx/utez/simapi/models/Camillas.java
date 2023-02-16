package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document(collection = "camillas")
public class Camillas {
    @Id
    private String idCamilla;
    private boolean estado;
    private String idPaciente;
    private String idIsla;
    private String idSala;
    private String idEnfermera;
}
