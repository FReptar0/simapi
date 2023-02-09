package mx.utez.simapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.utez.simapi.models.Salas;

public interface SalasRepository extends MongoRepository<Salas, String>{
    
}
