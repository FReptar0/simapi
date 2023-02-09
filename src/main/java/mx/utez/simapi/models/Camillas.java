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
@Document(collection = "camillas")
public class Camillas {
    @Id
    private Long idCamilla;
    private String nombre;
    private String descripcion;
    private boolean estado;
    private Long idPaciente;
    private Long idIsla;
    private Long idSala;
    private Long idEnfermera;
}
