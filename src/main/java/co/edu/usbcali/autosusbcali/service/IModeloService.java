package co.edu.usbcali.autosusbcali.service;

import co.edu.usbcali.autosusbcali.dto.request.CrearModificarModeloRequest;
import co.edu.usbcali.autosusbcali.dto.response.ObtenerModeloResponse;
import java.util.List;

public interface IModeloService {

    List<ObtenerModeloResponse> obtenerTodos();
    List<ObtenerModeloResponse> buscarModelosPorMarca(Long marcaId) throws Exception;
    ObtenerModeloResponse obtenerModeloPorNombre(String nombre);
    ObtenerModeloResponse obtenerModeloPorId(Long id);
    ObtenerModeloResponse crearModelo(CrearModificarModeloRequest crearModificarModeloRequest) throws Exception;
    ObtenerModeloResponse modificarModelo(Long id, CrearModificarModeloRequest crearModificarModeloRequest) throws Exception;
    void eliminarModelo(Long id) throws Exception;

    // Metodo para crear un modelo, ELIMINACION FISICA
//    void eliminarModeloFisico(Long id) throws Exception;

}
