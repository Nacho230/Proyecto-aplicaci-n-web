package com.soquetes.Loquetes.repositorios;

import com.soquetes.Loquetes.entidades.Orden_compra;
import com.soquetes.Loquetes.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Orden_compra_repositorio extends JpaRepository<Orden_compra, Long> {

    List<Orden_compra> findByUsuario(Usuario usuario);
}
