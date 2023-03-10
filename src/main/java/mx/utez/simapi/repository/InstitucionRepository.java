package mx.utez.simapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.utez.simapi.models.Institucion;

public interface InstitucionRepository extends MongoRepository<Institucion, String> {
    
}
