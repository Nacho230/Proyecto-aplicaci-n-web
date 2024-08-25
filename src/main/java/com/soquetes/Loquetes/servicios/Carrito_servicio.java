package com.soquetes.Loquetes.servicios;

import com.soquetes.Loquetes.entidades.Carrito;
import com.soquetes.Loquetes.entidades.Media;
import com.soquetes.Loquetes.entidades.Usuario;
import com.soquetes.Loquetes.repositorios.Carrito_repositorio;
import com.soquetes.Loquetes.repositorios.Media_repositorio;
import com.soquetes.Loquetes.repositorios.Usuario_repositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Carrito_servicio {

    private final Carrito_repositorio carrito_repositorio;
    private final Usuario_repositorio usuario_repositorio;
    private final Media_repositorio media_repositorio;

    public Carrito_servicio(Carrito_repositorio carrito_repositorio, Usuario_repositorio usuario_repositorio,
                            Media_repositorio media_repositorio){
        this.carrito_repositorio = carrito_repositorio;
        this.usuario_repositorio = usuario_repositorio;
        this.media_repositorio = media_repositorio;
    }

    @Transactional
    public void agregarAlCarrito(Long usuarioId, Long mediaId, Integer cantidad){

        if (cantidad <= 0){
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }

        Optional<Usuario> respuestaUsuario = usuario_repositorio.findById(usuarioId);
        Optional<Media> respuestaMedia = media_repositorio.findById(mediaId);

        if (respuestaUsuario.isPresent() && respuestaMedia.isPresent()){
            Usuario usuario = respuestaUsuario.get();
            Media media = respuestaMedia.get();

            //Buscar si el carrito ya tiene el producto
            Carrito carrito = carrito_repositorio.findByUsuarioAndMedia(usuario,media)
                    .orElse(new Carrito(usuario, media, 0));

            //Actualizar la cantidad
            carrito.setCantidad(carrito.getCantidad() + cantidad);
            carrito_repositorio.save(carrito);
        }else{
            throw new RuntimeException("Usuario o media no encontrado");
        }


    }

    @Transactional
    public void eliminarDelCarrito(Long usuarioId, Long mediaId){

        Optional<Usuario> respuestaUsuario = usuario_repositorio.findById(usuarioId);
        Optional<Media> respuestaMedia = media_repositorio.findById(mediaId);

        if (respuestaUsuario.isPresent() && respuestaMedia.isPresent()){
            Usuario usuario = respuestaUsuario.get();
            Media media = respuestaMedia.get();

            Carrito carrito = carrito_repositorio.findByUsuarioAndMedia(usuario,media).get();

            carrito_repositorio.delete(carrito);
        }else{
            throw new RuntimeException("Error en el carrito");
        }

    }


    @Transactional
    public void actualizarCantidad(Long usuarioId, Long mediaId, Integer cantidad) {

        if (cantidad < 0){
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }


        Optional<Usuario> respuestaUsuario = usuario_repositorio.findById(usuarioId);
        Optional<Media> respuestaMedia = media_repositorio.findById(mediaId);

        if (respuestaUsuario.isPresent() && respuestaMedia.isPresent()) {
            Usuario usuario = respuestaUsuario.get();
            Media media = respuestaMedia.get();

            Carrito carrito = carrito_repositorio.findByUsuarioAndMedia(usuario, media)
                    .orElseThrow(() -> new RuntimeException("El producto no está en el carrito"));

            carrito.setCantidad(cantidad);
            carrito_repositorio.save(carrito);


        } else {
            throw new RuntimeException("Usuario o medias no encontrados");
        }

    }


    public List<Carrito> listarProductos(Long usuarioId){
        Optional<Usuario> respuestaUsuario = usuario_repositorio.findById(usuarioId);

        if (respuestaUsuario.isPresent()){
            Usuario usuario = respuestaUsuario.get();
            return carrito_repositorio.findByUsuario(usuario);
        }else{
            throw new RuntimeException("Usuario no encontrado");
        }
    }


}
