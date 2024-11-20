package com.valencia.proyecto1evaluacion.modelos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ong_acontecimiento", schema = "safe_hand", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OngAcontecimiento {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_ong", nullable = false)
        private Ong ong;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_acontecimiento", nullable = false)
        private Acontecimiento acontecimiento;


}
