package co.edu.usbcali.autosusbcali.service;

import co.edu.usbcali.autosusbcali.domain.Marca;
import co.edu.usbcali.autosusbcali.domain.Modelo;
import co.edu.usbcali.autosusbcali.dto.request.CrearModificarModeloRequest;
import co.edu.usbcali.autosusbcali.dto.response.ObtenerModeloResponse;
import co.edu.usbcali.autosusbcali.mapper.ModeloMapper;
import co.edu.usbcali.autosusbcali.repository.MarcaRepository;
import co.edu.usbcali.autosusbcali.repository.ModeloRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j

public class ModeloService implements IModeloService {
    private final ModeloRepository modeloRepository;
    private final MarcaRepository marcaRepository;

    @Override
    public List<ObtenerModeloResponse> obtenerTodos() {
        log.info("Iniciando obtenerTodos()");
        return ModeloMapper.listModelosAListObtenerModeloResponse(modeloRepository.findAll());
    }

    @Override
    public List<ObtenerModeloResponse> buscarModelosPorMarca(Long marcaId) throws Exception {
        if (marcaId == null) {
            throw new Exception("El ID de la marca NO puede ser nulo");
        }
        if (!marcaRepository.existsById(marcaId)) {
            throw new Exception("No existe una marca con el ID " + marcaId);
        }

        List<Modelo> modelosPorMarca = modeloRepository.findByMarcaId(marcaId);
        return ModeloMapper.listModelosAListObtenerModeloResponse(modelosPorMarca);
    }

    @Override
    public ObtenerModeloResponse obtenerModeloPorNombre(String nombre) {
        Modelo modeloPorNombre = modeloRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado el modelo por nombre: " + nombre));
        return ModeloMapper.modeloObtenerModeloResponse(modeloPorNombre);
    }

    @Override
    public ObtenerModeloResponse obtenerModeloPorId(Long id) {
        Modelo modeloPorId = modeloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado el modelo por id: " + id));

        return ModeloMapper.modeloObtenerModeloResponse(modeloPorId);
    }

    @Override
    public ObtenerModeloResponse crearModelo(CrearModificarModeloRequest crearModificarModeloRequest) throws Exception {
        // Validaciones del request
        validarRequest(crearModificarModeloRequest);

        // Buscar la marca a la que pertenecera el modelo
        Marca marca = obtenerMarcaActiva(crearModificarModeloRequest.marcaId());

        // Validar que no exista un modelo con el mismo nombre
        if (modeloRepository.existsByNombre(crearModificarModeloRequest.nombre())) {
            throw new Exception("Ya existe un modelo con el mismo nombre.");
        }
        // convertir DTO a Domain
        Modelo modelo = ModeloMapper.crearModificarModeloRequestAModelo(crearModificarModeloRequest, marca);

        // Persistir
        modelo = modeloRepository.save(modelo);

        return ModeloMapper.modeloObtenerModeloResponse(modelo);
    }

    @Override
    public ObtenerModeloResponse modificarModelo(Long id, CrearModificarModeloRequest crearModificarModeloRequest) throws Exception {
        if (id == null) {
            throw new Exception("El ID NO puede ser nulo");
        }
        validarRequest(crearModificarModeloRequest);

        // buscar el modelo q se quiere modificar
        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe un modelo con el ID " + id));

        Marca marca = obtenerMarcaActiva(crearModificarModeloRequest.marcaId());

        // Validar que el nuevo nombre no lo tenga OTRO MODELO
        if (!modelo.getNombre().equals(crearModificarModeloRequest.nombre()) && modeloRepository.existsByNombre(crearModificarModeloRequest.nombre())) {
            throw new Exception("Ya existe otro modelo con el mismo nombre.");
        }

        // Actualizar el objeto existente, UPDATE, no Insert
        ModeloMapper.actualizarModeloDesdeRequest(modelo, crearModificarModeloRequest, marca);

        modelo = modeloRepository.save(modelo);
        return ModeloMapper.modeloObtenerModeloResponse(modelo);
    }

    @Override
    public void eliminarModelo(Long id) throws Exception {
        if (id == null) {
            throw new Exception("El ID NO puede ser nulo");
        }

        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() -> new Exception("No existe un modelo con el ID " + id));

        modeloRepository.delete(modelo);
    }

    // Metodo privados de apoyo
    private void validarRequest(CrearModificarModeloRequest req) throws Exception {
        if (req == null) {
            throw new Exception("El objeto CrearModificarModeloRequest no puede ser nulo.");
        }
        if (req.nombre() == null || req.nombre().trim().isBlank()) {
            throw new Exception("El nombre no puede ser nulo ni estar vacio");
        }

        if (req.marcaId() == null) {
            throw new Exception("El ID de la marca no puede ser nulo");
        }
    }

    private Marca obtenerMarcaActiva(Long marcaId) throws Exception {
        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new Exception("No existe una marca con el id " + marcaId));

        if (!marca.getActivo()) {
            throw new Exception("No se pued asignar un modelo a una marca inactica");
        }

        return marca;
    }

}