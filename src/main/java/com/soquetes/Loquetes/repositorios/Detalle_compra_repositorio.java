package com.soquetes.Loquetes.repositorios;

import com.soquetes.Loquetes.entidades.Detalle_compra;
import com.soquetes.Loquetes.entidades.Orden_compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Detalle_compra_repositorio extends JpaRepository<Detalle_compra, Long> {
    List<Detalle_compra> findByOrden_compra(Orden_compra orden);
}
