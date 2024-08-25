package com.soquetes.Loquetes.controladores;

import com.soquetes.Loquetes.servicios.Carrito_servicio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carrito")
public class Carrito_controlador {

    private final Carrito_servicio carrito_servicio;

    public Carrito_controlador(Carrito_servicio carrito_servicio){

        this.carrito_servicio = carrito_servicio;

    }


    @PostMapping("/agregar")




}

