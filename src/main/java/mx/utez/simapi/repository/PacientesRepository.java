package mx.utez.simapi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.utez.simapi.models.Pacientes;

public interface PacientesRepository extends MongoRepository<Pacientes, String> {
    Optional<Pacientes> findById(String idPaciente);
    
}
