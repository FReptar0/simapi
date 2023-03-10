package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "usuarios")
public class Usuarios {
    @Id
    private String idUsuario;
    private String nombre;
    private String apellidos;
    private String correo;
    private String contrasena;
    private String rol;
}
