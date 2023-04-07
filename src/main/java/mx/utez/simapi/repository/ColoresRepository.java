package mx.utez.simapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.utez.simapi.models.Colores;

public interface ColoresRepository extends MongoRepository<Colores, String> {
    Colores findByIdInstitucion(String idInstitucion);
}
