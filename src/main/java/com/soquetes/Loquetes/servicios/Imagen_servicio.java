package com.soquetes.Loquetes.servicios;

import com.soquetes.Loquetes.entidades.Imagen;
import com.soquetes.Loquetes.repositorios.Imagen_repositorio;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class Imagen_servicio {

    private final Imagen_repositorio imagen_repositorio;

    public Imagen_servicio(Imagen_repositorio imagen_repositorio){
        this.imagen_repositorio = imagen_repositorio;
    }

    public Imagen guardar(MultipartFile archivo){
        if (archivo != null){
            try{
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagen_repositorio.save(imagen);
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }

    public Imagen actualizar(MultipartFile archivo, Long id){
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();

                if (id != null) {
                    Optional<Imagen> respuesta = imagen_repositorio.findById(id);
                    if (respuesta.isPresent()) {

                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagen_repositorio.save(imagen);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }

}
