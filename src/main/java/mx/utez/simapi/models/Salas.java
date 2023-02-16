package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "salas")
public class Salas {
    @Id
    private String idSala;
    private String numeroDeSala;
    private String cantidadDeCamillas;
    private boolean estado;
    private String idJefeDeEnfermeria;
    private String idEnfermeraResponsable;
}
