package mx.utez.simapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.utez.simapi.models.Historial;

public interface HistorialRepository extends MongoRepository<Historial, String> {
    public List<Historial> findByIdInstitucion(String idInstitucion);
    public Historial findTopByOrderByIdCamillaDesc(String idCamilla);
}
