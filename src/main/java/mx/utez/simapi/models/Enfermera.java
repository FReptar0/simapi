package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
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
