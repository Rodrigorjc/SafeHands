package com.valencia.proyecto1evaluacion.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "perfilProveedores", schema = "safe_hand", catalog = "postgres")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PerfilProveedores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name="mail")
    private String mail;

    @Column(name="dni")
    private String dni;

    @Column(name="fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name="imagen_url")
    private String foto;
}
