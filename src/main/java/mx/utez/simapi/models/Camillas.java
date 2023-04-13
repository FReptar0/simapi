package mx.utez.simapi.models;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Camillas {
    @Id
    private String idCamillas;
    private String nombre;
    private String numeroExpediente;
    private String idInstitucion;
    private List<String> idEnfermera;
    private boolean estado;
    private boolean estadoAlarma;
    private int idSala; 
    private int idIsla; 
    private int idBoton;
}