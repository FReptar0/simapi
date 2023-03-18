package mx.utez.simapi.models;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsuarioLogin {
    @NotBlank(message = "El correo es obligatorio")
    private String correo;
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
