package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "enfermeras")
public class Enfermera {
    @Id
    private Long idEnfermera;
    private String nombre;
    private String apellidos;
    private String correo;
    private String contrasena;
    private Long idSala;
    private Long idIsla;
    private String rol;
    private Long idJefe;
}
