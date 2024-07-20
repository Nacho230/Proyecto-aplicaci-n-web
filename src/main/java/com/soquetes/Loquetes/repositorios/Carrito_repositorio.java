package com.soquetes.Loquetes.repositorios;

import com.soquetes.Loquetes.entidades.Carrito;
import com.soquetes.Loquetes.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Carrito_repositorio extends JpaRepository<Carrito,Long> {
    Optional<Carrito> findByUsuario(Usuario usuario);
}
