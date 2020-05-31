package com.business.manager.horario.dao.repositories;

import com.business.manager.horario.dao.entities.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Integer> {
	Parametro findByNombre(String nombre);
}
