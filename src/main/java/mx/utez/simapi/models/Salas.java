package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Salas {
    @Id
    private Long idSala;
    private Long numeroDeSala;
    private Long cantidadDeCamillas;
    private boolean estado;
    private Long idJefeDeEnfermeria;
    private Long idEnfermeraResponsable;
}
