package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Colores {
    @Id
    String idColores;
    String colorPrimario;
    String colorSecundario;
    String colorTerciario;
    String idInstitucion;
}
