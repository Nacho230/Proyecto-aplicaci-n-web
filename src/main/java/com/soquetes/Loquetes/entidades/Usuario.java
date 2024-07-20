package com.soquetes.Loquetes.entidades;

import com.soquetes.Loquetes.enumeraciones.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    protected String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    protected String apellido;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe contener al menos 6 caracteres")
    protected String password;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Por favor ingresar un email valido")
    protected String email;

    private LocalDateTime fecha_creacion;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Orden_compra> ordenes;

    @Enumerated(EnumType.STRING)
    protected Rol rol;
    @PrePersist
    protected void creacion(){ // Pequeña función que otorga una fecha de creacion a la entidad previo a persistirse
        fecha_creacion = LocalDateTime.now();
    }

}
