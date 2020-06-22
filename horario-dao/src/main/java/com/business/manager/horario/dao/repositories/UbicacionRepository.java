package com.business.manager.horario.dao.repositories;

import com.business.manager.horario.dao.entities.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {
    //@Query("select u from Ubicacion u where horariosByUbicacion is null")
    List<Ubicacion> findByHorariosByUbicacionIsNull();
}
