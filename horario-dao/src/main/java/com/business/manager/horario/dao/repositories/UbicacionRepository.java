package com.business.manager.horario.dao.repositories;

import com.business.manager.horario.dao.entities.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {
}
