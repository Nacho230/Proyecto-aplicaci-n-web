package com.soquetes.Loquetes.entidades;

import com.soquetes.Loquetes.enumeraciones.Tipo_media;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medias")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    protected String nombre;

    protected String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio debe ser igual o mayor a cero")
    protected Integer precio;

    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "El stock debe ser igual o mayor a cero")
    protected Integer stock; //Cant de stock

    @NotNull(message = "El estado del producto es obligatorio")
    protected Boolean alta_stock; //Tiene stock o no

    private LocalDateTime fecha_creacion;

    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Detalle_compra> detalle_compras;

    @Enumerated(EnumType.STRING)
    protected Tipo_media tipo;

    protected Boolean alta_sistema;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagen> imagenes = new ArrayList<>();

    @PrePersist
    protected void creacion(){ // Pequeña función que otorga una fecha de creacion a la entidad previo a persistirse
        fecha_creacion = LocalDateTime.now();
    }
}
