package mx.utez.simapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.utez.simapi.models.Administradores;

public interface AdministradoresRepository extends MongoRepository<Administradores, String>{
    
}
