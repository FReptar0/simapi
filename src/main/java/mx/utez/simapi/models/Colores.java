package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "colores")
public class Colores {
    @Id
    String idColores;
    String idInstitucion;
    String colorPrimario;
    String colorSecundario;
    String colorTerciario;
}
