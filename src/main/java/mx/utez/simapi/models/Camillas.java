package mx.utez.simapi.models;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Camillas {
    @Id
    private String idCamillas;
    private String nombre;
    private String numeroExpediente;
    private String idInstitucion;
    private String idEnfermera;
    private boolean estado;
    private boolean estadoAlarma;
    private int idSala; 
    private int idIsla; 
}