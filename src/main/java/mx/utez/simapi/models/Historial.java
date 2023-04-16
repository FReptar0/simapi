package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "historial")
public class Historial {
    @Id
    private String idHistorial;
    private String fechaPeticion;
    private String horaDePeticion;
    private String fechaAtencion;
    private String horaDeAtencion;
    private String idInstitucion;
    private String idPaciente;
    private String idEnfermera;
    private String idCamilla;
}
