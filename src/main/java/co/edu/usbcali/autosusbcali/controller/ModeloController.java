package co.edu.usbcali.autosusbcali.controller;

import co.edu.usbcali.autosusbcali.dto.request.CrearModificarModeloRequest;
import co.edu.usbcali.autosusbcali.dto.response.ObtenerModeloResponse;
import co.edu.usbcali.autosusbcali.service.IModeloService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/modelo")
@AllArgsConstructor
public class ModeloController {

    private final IModeloService modeloService;

    @GetMapping
    public ResponseEntity<List<ObtenerModeloResponse>> obtenerModelos() {
        return ResponseEntity.ok(modeloService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObtenerModeloResponse> obtenerModeloPorId(@PathVariable Long id) {
        return ResponseEntity.ok(modeloService.obtenerModeloPorId(id));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ObtenerModeloResponse> obtenerModeloPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(modeloService.obtenerModeloPorNombre(nombre));
    }

    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<List<ObtenerModeloResponse>> buscarModelosPorMarca(@PathVariable Long marcaId) throws Exception {
        return ResponseEntity.ok(modeloService.buscarModelosPorMarca(marcaId));
    }

    @PostMapping("/guardar")
    public ResponseEntity<ObtenerModeloResponse> creaModelo(
            @RequestBody CrearModificarModeloRequest crearModificarModeloRequest
            ) throws Exception {
        return new ResponseEntity<>(modeloService.crearModelo(crearModificarModeloRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObtenerModeloResponse> modificarModelo(
            @PathVariable Long id,
            @RequestBody CrearModificarModeloRequest crearModificarModeloRequest
    ) throws Exception {
        return ResponseEntity.ok(modeloService.modificarModelo(id, crearModificarModeloRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ObtenerModeloResponse> eliminarModelo(@PathVariable Long id) throws Exception {
        modeloService.eliminarModelo(id);
        return ResponseEntity.noContent().build();
    }
}
