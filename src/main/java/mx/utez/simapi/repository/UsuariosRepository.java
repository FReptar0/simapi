package mx.utez.simapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.utez.simapi.models.Usuarios;

public interface UsuariosRepository  extends MongoRepository<Usuarios, String>{
    
}
