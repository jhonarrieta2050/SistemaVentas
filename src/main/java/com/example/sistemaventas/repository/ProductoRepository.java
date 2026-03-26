package com.example.sistemaventas.repository;

import com.example.sistemaventas.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}

