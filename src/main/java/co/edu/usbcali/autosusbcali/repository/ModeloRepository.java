package co.edu.usbcali.autosusbcali.repository;

import co.edu.usbcali.autosusbcali.domain.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    Optional<Modelo> findByNombre(String nombre);
//    List<Modelo> findModeloById(long id);
    List<Modelo> findByMarcaId(Long marcaId);
    boolean existsByNombre(String nombre);
}
