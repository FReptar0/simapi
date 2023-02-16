package mx.utez.simapi.models;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "administradores")
public class Administradores {
    @Id
    private String idAdministrador;
    private String nombre;
    private String apellidos;
    private String correo;
    private String contrasena;
    private String rol;
}
