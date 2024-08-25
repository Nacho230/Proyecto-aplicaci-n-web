package com.soquetes.Loquetes.servicios;

import com.soquetes.Loquetes.entidades.Orden_compra;
import com.soquetes.Loquetes.entidades.Usuario;
import com.soquetes.Loquetes.repositorios.Detalle_compra_repositorio;
import com.soquetes.Loquetes.repositorios.Orden_compra_repositorio;
import com.soquetes.Loquetes.repositorios.Usuario_repositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class Orden_compra_servicio {

   private final Orden_compra_repositorio orden_compra_repositorio;
   private final Usuario_repositorio usuario_repositorio;
   private final Detalle_compra_servicio detalle_compra_servicio;

   public Orden_compra_servicio(Orden_compra_repositorio orden_compra_repositorio,
                                Usuario_repositorio usuario_repositorio,
                                Detalle_compra_servicio detalle_compra_servicio){

       this.orden_compra_repositorio = orden_compra_repositorio;
       this.usuario_repositorio = usuario_repositorio;
       this.detalle_compra_servicio = detalle_compra_servicio;

   }

   @Transactional
    public void crearOrden(Long usuarioId){

       Optional<Usuario> respuestaUsuario = usuario_repositorio.findById(usuarioId);

       if (respuestaUsuario.isPresent()){

           Usuario usuario = respuestaUsuario.get();
           Orden_compra orden = new Orden_compra();
           orden.setUsuario(usuario);
           orden.setFecha_compra(new Date());
           orden.setEstado("Pendiente");

           //guardamos otra orden para poder obtener su id
           Orden_compra ordenGuardada = orden_compra_repositorio.save(orden);

           //creamos los detalles de la orden
           detalle_compra_servicio.crearDetalles(ordenGuardada);

           //actualizamos el total de la compra
           double total = detalle_compra_servicio.calcularTotal(ordenGuardada);
           ordenGuardada.setTotal(total);

           orden_compra_repositorio.save(ordenGuardada);

       }else{
           throw new RuntimeException("Usuario no encontrado");
       }

   }


   public List<Orden_compra> listarOrdenes(Long usuarioId){

       Optional<Usuario> respuestaUsuario = usuario_repositorio.findById(usuarioId);

       if (respuestaUsuario.isPresent()){
           return orden_compra_repositorio.findByUsuario(respuestaUsuario.get());
       }else{
           throw new RuntimeException("Usuario no encontrado");
       }

   }


}
