package com.soquetes.Loquetes.repositorios;


import com.soquetes.Loquetes.entidades.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Media_repositorio extends JpaRepository<Media, Long> {


}
