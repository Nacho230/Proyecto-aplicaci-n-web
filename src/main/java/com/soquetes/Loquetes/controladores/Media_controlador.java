package com.soquetes.Loquetes.controladores;

import com.soquetes.Loquetes.entidades.Imagen;
import com.soquetes.Loquetes.entidades.Media;
import com.soquetes.Loquetes.servicios.Imagen_servicio;
import com.soquetes.Loquetes.servicios.Media_servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medias")
public class Media_controlador {

    private final Media_servicio media_servicio;
    private final Imagen_servicio imagen_servicio;

    @Autowired
    public Media_controlador(Media_servicio media_servicio, Imagen_servicio imagen_servicio){
        this.media_servicio = media_servicio;
        this.imagen_servicio = imagen_servicio;
    }

    @GetMapping
    public List<Media> listarMedias(){

        return media_servicio.listarMedias();

    }

    //Obtenemos una media(calcetin) por id
    @GetMapping("/{id}")
    public ResponseEntity<Media> obtenerMedia(@PathVariable Long id){

        Optional<Media> media = media_servicio.buscarPorId(id);

        if (media.isPresent()){
            return ResponseEntity.ok(media.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }


    @PostMapping
    public ResponseEntity<Media> crearMedia(@RequestParam String nombreProducto,
                                            @RequestParam String descripcion_producto,
                                            @RequestParam Integer precio,
                                            @RequestParam Integer stock,
                                            @RequestParam Boolean alta_stock,
                                            @RequestParam("imagenes")List<MultipartFile> archivos){

        try{
            //Una lista que almacena todas las imagenes de la media
            List<Imagen> imagenes = new ArrayList<>();

            for (MultipartFile archivo : archivos){
                Imagen imagen = imagen_servicio.guardar(archivo);
                if (imagen != null){
                    imagenes.add(imagen);
                }

            }


            Media media = media_servicio.crearMedia(nombreProducto,descripcion_producto,
                    precio,stock,alta_stock,imagenes);
            return ResponseEntity.status(HttpStatus.CREATED).body(media);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }




    @PutMapping("/{id}")
    public ResponseEntity<Media> actualizarMedia(@PathVariable Long id,
                                                 @RequestParam String nombreProducto,
                                                 @RequestParam String descripcion_producto,
                                                 @RequestParam Integer precio,
                                                 @RequestParam Integer stock,
                                                 @RequestParam Boolean alta_stock,
                                                 @RequestParam(value = "imagen",required = false)List<MultipartFile> archivos ){

        try{

            //Una lista que almacena todas las imagenes de la media
            List<Imagen> imagenes = new ArrayList<>();

            if (archivos != null && !archivos.isEmpty()){

                for (MultipartFile archivo : archivos){
                    Imagen imagen = imagen_servicio.guardar(archivo);
                    if (imagen != null){
                        imagenes.add(imagen);
                    }
                }
            }

            Media media = media_servicio.ActualizarMedia(id, nombreProducto,descripcion_producto, precio,stock,alta_stock,imagenes);
            return ResponseEntity.ok(media);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedia(@PathVariable Long id){
        try{
            media_servicio.eliminarMedia(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }






}
