package com.valencia.proyecto1evaluacion.repositorios;

import com.valencia.proyecto1evaluacion.modelos.Ong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OngRepositorio extends JpaRepository <Ong, Integer>{
}
