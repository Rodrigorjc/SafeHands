package com.valencia.proyecto1evaluacion.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario", schema = "safe_hand", catalog = "postgres")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 500)
    private String password;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "rol", nullable = false)
    private Integer rol;

    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;  // Nueva columna para almacenar la URL de la imagen
}
