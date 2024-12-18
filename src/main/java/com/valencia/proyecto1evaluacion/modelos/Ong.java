package com.valencia.proyecto1evaluacion.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ong", schema = "safe_hand", catalog = "postgres")
@Getter
@Setter
@ToString(exclude = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "num_voluntarios", nullable = false)
    private Integer numVoluntarios;

    @Column(name = "sede", nullable = false, length = 100)
    private String sede;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Column(name = "ubicacion", nullable = false, length = 500)
    private String ubicacion;

    @Column(name = "img",  length = 1500)
    private String img;

    @JsonBackReference("usuario-ong")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @JsonBackReference
    @OneToMany(mappedBy = "ong", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OngAcontecimiento> ongAcontecimientos;


}
