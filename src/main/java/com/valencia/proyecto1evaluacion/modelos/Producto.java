package com.valencia.proyecto1evaluacion.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "producto", schema = "safe_hand", catalog = "postgres")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @Column(name = "precio", nullable = false)
    private Float precio;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_proveedores", nullable = false)
    private Proveedores proveedores;





}