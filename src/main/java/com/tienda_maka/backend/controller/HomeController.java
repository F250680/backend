package com.tienda_maka.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "✅ Backend Tienda Maka funcionando");
        response.put("puerto", 8080);
        response.put("base_datos", "tienda_maka");
        response.put("endpoints", Map.of(
                "categorias", "GET /api/categorias",
                "productos", "GET /api/productos",
                "clientes", "GET /api/clientes",
                "pedidos", "GET /api/pedidos",
                "detalles_pedido", "GET /api/detalles-pedido",
                "crear_categoria", "POST /api/categorias",
                "crear_producto", "POST /api/productos",
                "crear_detalle_pedido", "POST /api/detalles-pedido"
        ));
        response.put("estado", "🟢 Conectado a MySQL");
        return response;
    }
}