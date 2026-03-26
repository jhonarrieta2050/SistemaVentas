package com.example.sistemaventas.service;

import com.example.sistemaventas.model.Producto;
import com.example.sistemaventas.model.Compra;
import com.example.sistemaventas.model.ProductoRegistroDTO;
import com.example.sistemaventas.repository.ProductoRepository;
import com.example.sistemaventas.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CompraRepository compraRepository;

    public Producto registrarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Compra comprarProducto(Long productoId, int cantidad) throws Exception {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        if (!productoOpt.isPresent()) {
            throw new Exception("Producto no encontrado");
        }
        Producto producto = productoOpt.get();
        if (producto.getCantidad() < cantidad) {
            throw new Exception("Cantidad insuficiente en stock");
        }
        producto.setCantidad(producto.getCantidad() - cantidad);
        productoRepository.save(producto);
        double total = producto.getPrecio() * cantidad;
        Compra compra = new Compra();
        compra.setProductoId(productoId);
        compra.setCantidadComprada(cantidad);
        compra.setTotal(total);
        compra.setFecha(LocalDateTime.now());
        return compraRepository.save(compra);
    }

    public List<Compra> historialCompras() {
        return compraRepository.findAll();
    }

    public boolean eliminarProducto(Long id) {
        Optional<Producto> productoOpt = productoRepository.findById(id);
        if (productoOpt.isPresent()) {
            productoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Producto actualizarProducto(Long id, ProductoRegistroDTO dto) throws Exception {
        Optional<Producto> productoOpt = productoRepository.findById(id);
        if (!productoOpt.isPresent()) {
            throw new Exception("Producto no encontrado");
        }
        Producto producto = productoOpt.get();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setCantidad(dto.getStock());
        return productoRepository.save(producto);
    }
}
