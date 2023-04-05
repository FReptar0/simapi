package mx.utez.simapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import mx.utez.simapi.models.Institucion;

public interface InstitucionRepository extends MongoRepository<Institucion, String> {
    public boolean existsByNombre(String nombre);
    public boolean existsByCorreo(String correo);
    public Institucion findByCorreo(String correo); 
}
