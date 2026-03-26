package com.example.sistemaventas.controller;

import com.example.sistemaventas.model.Producto;
import com.example.sistemaventas.model.Compra;
import com.example.sistemaventas.model.ProductoRegistroDTO;
import com.example.sistemaventas.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")

public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/productos")
    public ResponseEntity<Producto> registrarProducto(@Valid @RequestBody ProductoRegistroDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setCantidad(productoDTO.getStock());
        return ResponseEntity.ok(productoService.registrarProducto(producto));
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @PostMapping("/compras")
    public ResponseEntity<?> comprarProducto(@RequestParam Long productoId, @RequestParam int cantidad) {
        try {
            Compra compra = productoService.comprarProducto(productoId, cantidad);
            return ResponseEntity.ok(compra);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/compras/historial")
    public ResponseEntity<List<Compra>> historialCompras() {
        return ResponseEntity.ok(productoService.historialCompras());
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        boolean eliminado = productoService.eliminarProducto(id);
        if (eliminado) {
            return ResponseEntity.ok().body("Producto eliminado correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoRegistroDTO productoDTO) {
        try {
            Producto actualizado = productoService.actualizarProducto(id, productoDTO);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
