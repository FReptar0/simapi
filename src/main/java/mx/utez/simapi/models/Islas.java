package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Islas {
    @Id
    private Long idIsla;
    private Long numeroDeIsla;
    private boolean estado;
    private Long idSala;
    private Long idEnfermeraResponsable;
    private Long idJefeDeEnfermeria;
}
