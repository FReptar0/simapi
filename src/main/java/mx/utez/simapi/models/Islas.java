package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Islas {
    @Id
    private Long idIsla;
    private Long numeroDeIsla;
    private boolean estado;
    private Long idSala;
    private Long idEnfermeraResponsable;
    private Long idJefeDeEnfermeria;
}
