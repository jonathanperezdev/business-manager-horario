package com.business.manager.horario.dao.repositories;

import com.business.manager.horario.dao.entities.HorarioUbicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioUbicacionRepository extends JpaRepository<HorarioUbicacion, Long> {
}
