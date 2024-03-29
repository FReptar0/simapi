package mx.utez.simapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.utez.simapi.models.Colores;

public interface ColoresRepository extends MongoRepository<Colores, String> {
    public Colores findByIdColores(String idColores);
    public Colores findByIdInstitucion(String idInstitucion);
}
