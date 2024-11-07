package com.valencia.proyecto1evaluacion.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "acontecimiento", schema = "safe_hand", catalog = "postgres")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class Aconteciminto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Column(name = "ubicacion", nullable = false, length = 500)
    private String ubicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ong", nullable = false)
    private Ong ong;
}
