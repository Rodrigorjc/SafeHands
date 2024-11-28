package com.valencia.proyecto1evaluacion.modelos;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cliente", schema = "safe_hand", catalog = "postgres")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "dni", nullable = false, length = 9)
    private String dni;

    @Column(name = "foto_perfil", nullable = true)
    private String fotoPerfil = "default.jpg";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Pedido> pedidos;
}
