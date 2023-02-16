package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "islas")
public class Islas {
    @Id
    private String idIsla;
    private String numeroDeIsla;
    private boolean estado;
    private String idSala;
    private String idEnfermeraResponsable;
    private String idJefeDeEnfermeria;
}
