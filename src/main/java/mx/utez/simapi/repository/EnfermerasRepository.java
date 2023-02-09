package mx.utez.simapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.utez.simapi.models.Enfermera;

public interface EnfermerasRepository extends MongoRepository<Enfermera, String>{
    
}
