package mx.utez.simapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import mx.utez.simapi.models.Camillas;

public interface CamillasRepository extends MongoRepository<Camillas, String> {
    public List<Camillas> findByIdInstitucion(String idInstitucion);

    public List<Camillas> findByIdEnfermera(String idEnfermera);

    public List<Camillas> findByIdSala(int idSala);

    public List<Camillas> findByIdIsla(int idIsla);

    public Camillas findByIdBotonAndIdInstitucion(int idBoton, String idInstitucion);

    @Query("{'idEnfermera.?0': { $in: [ ?1 ] }}")
    List<Camillas> findCamillasByTurnoAndId(String turno, String id);
}
