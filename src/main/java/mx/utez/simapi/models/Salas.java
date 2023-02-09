package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Salas {
    @Id
    private Long idSala;
    private Long numeroDeSala;
    private Long cantidadDeIslas;
    private Long cantidadDeCamillas;
    private boolean estado;
    private Long idJefeDeEnfermeria;
    private Long idAdministrador;
    private Long idEnfermeraResponsable;
}
