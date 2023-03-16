package mx.utez.simapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import mx.utez.simapi.models.Usuarios;

public interface UsuariosRepository extends MongoRepository<Usuarios, String> {
    public Usuarios findByCorreo(String correo);

    @Query(value = "{ '_id' : ?0 }", fields = "{ 'nombre' : 1, 'apellidos' : 1, 'correo' : 1}")
    Usuarios fbyIdUsuarios(String id);
}
