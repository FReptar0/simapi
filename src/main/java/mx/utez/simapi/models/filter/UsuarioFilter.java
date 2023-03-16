package mx.utez.simapi.models.filter;

import lombok.Data;

@Data
public class UsuarioFilter {
    private String nombre;
    private String apellidos;
    private String correo;
}
