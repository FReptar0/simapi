package mx.utez.simapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.utez.simapi.models.Islas;

public interface IslasRepository extends MongoRepository<Islas, String>{
    Islas findByNumeroDeIsla(String nombreDeIsla);
}
