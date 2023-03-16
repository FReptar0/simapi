package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "institucion")
public class Institucion {
    @Id
    private String idInstitucion;
    private String nombre;
    private String correo;
    private String password;
    private String logo;
    private int cantidadCamillas;
    private int cantidadDeSalas;
}
