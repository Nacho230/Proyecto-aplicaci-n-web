package com.soquetes.Loquetes.servicios;

import com.soquetes.Loquetes.entidades.Media;
import com.soquetes.Loquetes.repositorios.Media_repositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class Media_servicio {

    private final Media_repositorio mediaRepositorio;

    public Media_servicio(Media_repositorio mediaRepositorio){
        this.mediaRepositorio = mediaRepositorio;
    }

    /* Terminar luego
    @Transactional
    public void agregarMedia(Long id, String nombre_producto, String descripcion_producto, Integer precio,
                             Integer stock, Boolean alta_stock){


        mediaRepositorio.save();
    }

    @Transactional
    public void ActualizarMedia(Long id, )

     */
}
