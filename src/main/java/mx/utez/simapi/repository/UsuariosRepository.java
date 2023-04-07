package mx.utez.simapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import mx.utez.simapi.models.Usuarios;

public interface UsuariosRepository extends MongoRepository<Usuarios, String> {
    public Usuarios findByCorreo(String correo);
    public Boolean existsByCorreo(String correo);
    public List<Usuarios> findByInstitucion(String idInstitucion);

    @Query(value = "{ '_id' : ?0 }", fields = "{ 'nombre' : 1, 'apellidos' : 1, 'correo' : 1}")
    Usuarios fbyIdUsuarios(String id);

    @Query(value = "{ 'rol' : 'SA' }", count = true)
    public int countSA();
}
