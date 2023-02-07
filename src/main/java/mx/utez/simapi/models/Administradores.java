package mx.utez.simapi.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "administradores")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Administradores {
    @Id
    private int idAdministrador;
    private String nombre;
    private String apellidos;
    private String correo;
    private String contrasena;
    
}
