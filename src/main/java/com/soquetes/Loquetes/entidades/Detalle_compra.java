package com.soquetes.Loquetes.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalles_compra")
public class Detalle_compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private Orden_compra orden_compra;

    @ManyToOne
    @JoinColumn(name = "media_id", nullable = false)
    private Media media;

    private Integer cantidad;

    private Double precio;

}
