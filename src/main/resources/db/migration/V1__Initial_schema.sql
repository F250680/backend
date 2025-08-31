-- =============================================
-- Migración inicial para Tienda Maka
-- Cambia las columnas ID a BIGINT y recrea constraints
-- =============================================

-- Primero eliminar las constraints si existen
ALTER TABLE productos DROP FOREIGN KEY IF EXISTS productos_ibfk_1;
ALTER TABLE pedidos DROP FOREIGN KEY IF EXISTS pedidos_ibfk_1;
ALTER TABLE detalles_pedido DROP FOREIGN KEY IF EXISTS detalles_pedido_ibfk_1;
ALTER TABLE detalles_pedido DROP FOREIGN KEY IF EXISTS detalles_pedido_ibfk_2;

-- Modificar las columnas de IDs primarios a BIGINT
ALTER TABLE categorias MODIFY id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE clientes MODIFY id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE pedidos MODIFY id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE productos MODIFY id BIGINT NOT NULL AUTO_INCREMENT;

-- Modificar las columnas de claves foráneas a BIGINT
ALTER TABLE productos MODIFY categoria_id BIGINT;
ALTER TABLE pedidos MODIFY cliente_id BIGINT;
ALTER TABLE detalles_pedido MODIFY pedido_id BIGINT;
ALTER TABLE detalles_pedido MODIFY producto_id BIGINT;

-- Recrear las constraints de claves foráneas
ALTER TABLE productos
    ADD CONSTRAINT fk_productos_categoria
        FOREIGN KEY (categoria_id)
            REFERENCES categorias(id)
            ON DELETE SET NULL;

ALTER TABLE pedidos
    ADD CONSTRAINT fk_pedidos_cliente
        FOREIGN KEY (cliente_id)
            REFERENCES clientes(id)
            ON DELETE CASCADE;

ALTER TABLE detalles_pedido
    ADD CONSTRAINT fk_detalles_pedido
        FOREIGN KEY (pedido_id)
            REFERENCES pedidos(id)
            ON DELETE CASCADE;

ALTER TABLE detalles_pedido
    ADD CONSTRAINT fk_detalles_producto
        FOREIGN KEY (producto_id)
            REFERENCES productos(id)
            ON DELETE SET NULL;

-- =============================================
-- Comentarios adicionales para documentación
-- =============================================

-- Esta migración convierte todos los IDs de INT a BIGINT
-- para soportar un mayor número de registros y
-- evitar problemas de desbordamiento

-- Las constraints se recrean con opciones apropiadas:
-- ON DELETE CASCADE: Elimina registros hijos cuando se elimina el padre
-- ON DELETE SET NULL: Establece NULL en la clave foránea cuando se elimina el padre