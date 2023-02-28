package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "colores")
public class Colores {
    @Id
    private String idColor;
    private String institucion;
    private String primary;
    private String secondary;
    private String other;

}
