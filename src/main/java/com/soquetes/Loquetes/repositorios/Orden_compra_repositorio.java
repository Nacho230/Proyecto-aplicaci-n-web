package com.soquetes.Loquetes.repositorios;

import com.soquetes.Loquetes.entidades.Orden_compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Orden_compra_repositorio extends JpaRepository<Orden_compra, Long> {
}
