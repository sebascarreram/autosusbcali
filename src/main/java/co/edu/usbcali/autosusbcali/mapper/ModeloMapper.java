package co.edu.usbcali.autosusbcali.mapper;

import co.edu.usbcali.autosusbcali.domain.Modelo;
import co.edu.usbcali.autosusbcali.domain.Marca;
import co.edu.usbcali.autosusbcali.dto.request.CrearModificarModeloRequest;
import co.edu.usbcali.autosusbcali.dto.request.CrearModificarRolRequest;
import co.edu.usbcali.autosusbcali.dto.response.ObtenerModeloResponse;

import java.util.List;

public class ModeloMapper {
    private ModeloMapper() {
    }

    public static ObtenerModeloResponse modeloObtenerModeloResponse(Modelo modelo) {
        return new ObtenerModeloResponse(
                modelo.getId(),
                modelo.getMarca().getId(),
                modelo.getNombre()
        );
    }

    public static List<ObtenerModeloResponse> listModelosAListObtenerModeloResponse(List<Modelo> modelos) {
        return modelos.stream().map(ModeloMapper::modeloObtenerModeloResponse).toList();
    }

    // Para crear: construye un Modelo nuevo pero sin ID porque se genera la DB
    public static Modelo crearModificarModeloRequestAModelo(CrearModificarModeloRequest req, Marca marca) {
        Modelo modelo = new Modelo();
        modelo.setNombre(req.nombre());
        modelo.setMarca(marca);
        return modelo;
    }
    // Para modificar: actuaiza un Modelo que ya existe en la DB
    public static void actualizarModeloDesdeRequest(Modelo modelo, CrearModificarModeloRequest req, Marca marca) {
        modelo.setNombre(req.nombre());
        modelo.setMarca(marca);
    }
}
