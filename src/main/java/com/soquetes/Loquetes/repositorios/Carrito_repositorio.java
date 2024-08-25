package com.soquetes.Loquetes.repositorios;

import com.soquetes.Loquetes.entidades.Carrito;
import com.soquetes.Loquetes.entidades.Media;
import com.soquetes.Loquetes.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Carrito_repositorio extends JpaRepository<Carrito,Long> {
    Optional<Carrito> findByUsuarioAndMedia(Usuario usuario, Media media);

    List<Carrito> findByUsuario(Usuario usuario);

}
