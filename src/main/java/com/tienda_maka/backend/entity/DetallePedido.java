package com.tienda_maka.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal; // ← Asegúrate de tener este import

@Entity
@Table(name = "detalles_pedido")
@Data
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario; // ← Debe ser BigDecimal

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal; // ← Debe ser BigDecimal

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}