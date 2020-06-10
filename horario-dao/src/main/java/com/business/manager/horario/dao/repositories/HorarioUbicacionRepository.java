package com.business.manager.horario.dao.repositories;

import com.business.manager.horario.dao.entities.HorarioUbicacion;
import com.business.manager.horario.enums.DiaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioUbicacionRepository extends JpaRepository<HorarioUbicacion, Long> {
    @Query("select hu from HorarioUbicacion hu where ubicacion.id = :idUbicacion and dia = :dia")
    HorarioUbicacion findHorarioDefaultByUbicacionAndDia(Integer idUbicacion, DiaEnum dia);
}
