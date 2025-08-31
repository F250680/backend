package com.tienda_maka.backend.controller;

import com.tienda_maka.backend.entity.DetallePedido;
import com.tienda_maka.backend.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal; // ← Agregar este import
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; // ← Agregar este import también

@RestController
@RequestMapping("/api/detalles-pedido")
@CrossOrigin(origins = "http://localhost:5500")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    // GET: Obtener todos los detalles de pedido
    @GetMapping
    public List<DetallePedido> obtenerTodos() {
        return detallePedidoRepository.findAll();
    }

    // GET: Obtener detalle de pedido por ID
    @GetMapping("/{id}")
    public Optional<DetallePedido> obtenerPorId(@PathVariable Long id) {
        return detallePedidoRepository.findById(id);
    }

    // GET: Obtener detalles por ID de pedido
    @GetMapping("/pedido/{pedidoId}")
    public List<DetallePedido> obtenerPorPedidoId(@PathVariable Long pedidoId) {
        return detallePedidoRepository.findAll().stream()
                .filter(detalle -> detalle.getPedido().getId().equals(pedidoId))
                .collect(Collectors.toList());
    }

    // GET: Obtener detalles por ID de producto
    @GetMapping("/producto/{productoId}")
    public List<DetallePedido> obtenerPorProductoId(@PathVariable Long productoId) {
        return detallePedidoRepository.findAll().stream()
                .filter(detalle -> detalle.getProducto().getId().equals(productoId))
                .collect(Collectors.toList());
    }

    // POST: Crear nuevo detalle de pedido
    @PostMapping
    public DetallePedido crear(@RequestBody DetallePedido detallePedido) {
        // Calcular el subtotal automáticamente
        if (detallePedido.getPrecioUnitario() != null && detallePedido.getCantidad() != null) {
            BigDecimal precio = detallePedido.getPrecioUnitario();
            BigDecimal cantidad = BigDecimal.valueOf(detallePedido.getCantidad());
            detallePedido.setSubtotal(precio.multiply(cantidad));
        }
        return detallePedidoRepository.save(detallePedido);
    }

    // PUT: Actualizar detalle de pedido existente
    @PutMapping("/{id}")
    public DetallePedido actualizar(@PathVariable Long id, @RequestBody DetallePedido detallePedido) {
        detallePedido.setId(id);

        // Recalcular subtotal si es necesario
        if (detallePedido.getPrecioUnitario() != null && detallePedido.getCantidad() != null) {
            BigDecimal precio = detallePedido.getPrecioUnitario();
            BigDecimal cantidad = BigDecimal.valueOf(detallePedido.getCantidad());
            detallePedido.setSubtotal(precio.multiply(cantidad));
        }

        return detallePedidoRepository.save(detallePedido);
    }

    // DELETE: Eliminar detalle de pedido
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        detallePedidoRepository.deleteById(id);
    }

    // GET: Obtener estadísticas de ventas por producto
    @GetMapping("/estadisticas/ventas-productos")
    public List<Object[]> obtenerEstadisticasVentasPorProducto() {
        return detallePedidoRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        detalle -> detalle.getProducto().getId(),
                        Collectors.summarizingDouble(detalle ->
                                detalle.getSubtotal().doubleValue()
                        )
                ))
                .entrySet().stream()
                .map(entry -> new Object[]{
                        entry.getKey(),
                        entry.getValue().getSum(),
                        entry.getValue().getCount()
                })
                .collect(Collectors.toList());
    }
}