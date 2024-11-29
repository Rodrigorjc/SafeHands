package com.valencia.proyecto1evaluacion.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "proveedores", schema = "safe_hand", catalog = "postgres")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Proveedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "num_voluntarios", nullable = false)
    private Integer numVoluntarios;

    @Column(name = "sede", nullable = false, length = 100)
    private String sede;

    @Column(name = "ubicacion", nullable = false, length = 500)
    private String ubicacion;

    @Column(name = "cif", nullable = false, length = 9)
    private String cif;

    @Transient
    private Double totalRecaudado;

    @Column(name = "img")
    private String img;

    @Column(name="nombre",nullable = false)
    private String nombre;

    @Column(name = "validado", nullable = false)
    private Boolean validado = false;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}