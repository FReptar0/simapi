package mx.utez.simapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.utez.simapi.models.Camillas;

public interface CamillasRepository extends MongoRepository<Camillas, String>{
    
}
