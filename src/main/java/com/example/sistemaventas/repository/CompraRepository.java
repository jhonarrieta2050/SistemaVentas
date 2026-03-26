package com.example.sistemaventas.repository;

import com.example.sistemaventas.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}

