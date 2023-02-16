package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "pacientes")
public class Pacientes {
    @Id
    private String idPaciente;
    private String nombre;
    private String apellidos;
    private String descripcion;
}
