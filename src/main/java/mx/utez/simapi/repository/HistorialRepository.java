package mx.utez.simapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.utez.simapi.models.Historial;

public interface HistorialRepository extends MongoRepository<Historial, String>{
    
}
