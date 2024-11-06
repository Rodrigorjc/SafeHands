package com.valencia.proyecto1evaluacion.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ong", schema = "safe_hand", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Ong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name="num_voluntarios")
    private Integer numVoluntarios;
    @Column(name="sede")
    private String sede;
    @Column(name="ubicacion")
    private String ubicacion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_usuario")
    private Usuario usuario;

}
