package com.soquetes.Loquetes.repositorios;

import com.soquetes.Loquetes.entidades.Detalle_compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Detalle_compra_repositorio extends JpaRepository<Detalle_compra, Long> {
}
