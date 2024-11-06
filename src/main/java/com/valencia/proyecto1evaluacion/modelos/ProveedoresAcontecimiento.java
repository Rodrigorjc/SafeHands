package com.valencia.proyecto1evaluacion.modelos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "proveedores_acontecimiento", schema = "safe_hand", catalog = "postgres")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProveedoresAcontecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedores", nullable = false)
    private Proveedores proveedores;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_acontecimiento", nullable = false)
    private Acontecimiento acontecimiento;
}

