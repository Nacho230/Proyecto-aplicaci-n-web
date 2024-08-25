package com.soquetes.Loquetes.servicios;

import com.soquetes.Loquetes.entidades.Imagen;
import com.soquetes.Loquetes.entidades.Media;
import com.soquetes.Loquetes.repositorios.Media_repositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class Media_servicio {

    private final Media_repositorio media_repositorio;
    private final Imagen_servicio imagen_servicio;

    public Media_servicio(Media_repositorio media_repositorio, Imagen_servicio imagen_servicio){
        this.media_repositorio = media_repositorio;
        this.imagen_servicio = imagen_servicio;
    }


    @Transactional
    public Media crearMedia(String nombre_producto, String descripcion_producto, Integer precio,
                           Integer stock, Boolean alta_stock, List<Imagen> imagenes){


        Media media = new Media();
        media.setNombre(nombre_producto);
        media.setDescripcion(descripcion_producto);
        media.setPrecio(precio);
        media.setStock(stock);
        media.setAlta_stock(alta_stock);
        media.setAlta_sistema(true);
        media.setImagenes(imagenes);

        return media_repositorio.save(media);
    }

    @Transactional
    public Media ActualizarMedia(Long id,String nombre_producto, String descripcion_producto, Integer precio,
                                Integer stock, Boolean alta_stock, List<Imagen> imagenes){

        Optional<Media> respuesta = media_repositorio.findById(id);

        if (respuesta.isPresent()){
            Media media = respuesta.get();
            media.setNombre(nombre_producto);
            media.setDescripcion(descripcion_producto);
            media.setPrecio(precio);
            media.setStock(stock);
            media.setAlta_stock(alta_stock);

            if (imagenes != null && !imagenes.isEmpty()) {
                media.setImagenes(imagenes);
            }

            media_repositorio.save(media);
            return media_repositorio.save(media);

        }else{
            throw new RuntimeException("Media no encontrada");
        }



    }


    @Transactional
    public void eliminarMedia(Long id) {

        if (!media_repositorio.existsById(id)) {
            throw new NoSuchElementException("Media no encontrada");
        }
        media_repositorio.deleteById(id);
    }

    /* Este metodo está comentado por si necesito usarlo en el futuro.
    @Transactional
    public void bajarMedia(Long id){

        Optional<Media> respuesta = media_repositorio.findById(id);

        if (respuesta.isPresent()){
            Media media = respuesta.get();
            media.setAlta_sistema(!media.getAlta_sistema());

        }else{
            throw new NoSuchElementException("Media no encontrada");
        }

    }

     */

    public List<Media> listarMedias(){
        List<Media> medias = media_repositorio.findAll();
        if (medias.isEmpty()){
            throw new NoSuchElementException("No hay medias creadas");
        }else{
            return medias;
        }
    }

    @Transactional
    public Optional<Media> buscarPorId(Long id){
        return media_repositorio.findById(id);
    }


}
