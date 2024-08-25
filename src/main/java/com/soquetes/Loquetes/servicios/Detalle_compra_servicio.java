package com.soquetes.Loquetes.servicios;

import com.soquetes.Loquetes.entidades.Carrito;
import com.soquetes.Loquetes.entidades.Detalle_compra;
import com.soquetes.Loquetes.entidades.Orden_compra;
import com.soquetes.Loquetes.repositorios.Carrito_repositorio;
import com.soquetes.Loquetes.repositorios.Detalle_compra_repositorio;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Detalle_compra_servicio {

    private final Detalle_compra_repositorio detalle_compra_repositorio;
    private final Carrito_repositorio carrito_repositorio;

    public Detalle_compra_servicio(Detalle_compra_repositorio detalle_compra_repositorio,
                                   Carrito_repositorio carrito_repositorio){

        this.detalle_compra_repositorio = detalle_compra_repositorio;
        this.carrito_repositorio = carrito_repositorio;

    }


    @Transactional
    public void crearDetalles(Orden_compra orden){

        List<Carrito> productos = carrito_repositorio.findByUsuario(orden.getUsuario());

        for (Carrito carrito : productos){

            Detalle_compra detalle = new Detalle_compra();
            detalle.setOrden_compra(orden);
            detalle.setMedia(carrito.getMedia());
            detalle.setCantidad(carrito.getCantidad());
            detalle.setPrecio(carrito.getMedia().getPrecio());

            detalle_compra_repositorio.save(detalle);

        }

    }

    public double calcularTotal(Orden_compra orden){

        List<Detalle_compra> detalles = detalle_compra_repositorio.findByOrden_compra(orden);

        return detalles.stream().mapToDouble(d -> d.getPrecio() * d.getCantidad()).sum();

    }



}
