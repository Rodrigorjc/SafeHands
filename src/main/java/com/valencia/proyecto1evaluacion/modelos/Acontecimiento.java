package com.valencia.proyecto1evaluacion.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "acontecimiento", schema = "safe_hand", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Acontecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @Column(name="nombre")
    private String nombre;
    @Column(name="descripcion")
    private String descripcion;
    @Column(name="ubicacion")
    private String ubicacion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_ong")
    private Ong ong;
}
