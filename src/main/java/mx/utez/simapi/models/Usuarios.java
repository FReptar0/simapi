package mx.utez.simapi.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "usuarios")
public class Usuarios {
    @Id
    private String idUsuario;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellidos;
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo no es válido")
    private String correo;
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
    @NotBlank(message = "El rol no puede estar vacío")
    private String rol;
    @NotBlank(message = "La institución no puede estar vacía")
    private String idInstitucion;
}
