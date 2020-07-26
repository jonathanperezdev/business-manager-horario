package com.business.manager.horario.dao.repositories;

import com.business.manager.horario.dao.entities.DiaPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaPagoRepository extends JpaRepository<DiaPago, Long> {
    @Query("select d " +
            "from DiaPago d " +
            "where d.idSemana = :idSemana " +
            "Order by d.empleado.nombres, d.empleado.apellidos ")
    List<DiaPago> findBySemana(Long idSemana);

    @Query("select d " +
            "from DiaPago d " +
            "where d.idSemana = :idSemana " +
            "and d.idUbicacion = :idUbicacion " +
            "Order by d.empleado.nombres, d.empleado.apellidos ")
    List<DiaPago> findBySemanaAndUbicacion(Long idSemana, Integer idUbicacion);

    @Query("select d " +
            "from DiaPago d " +
            "where d.idSemana = :idSemana " +
            "and d.empleado.id = :idEmpleado ")
    List<DiaPago> findBySemanaAndEmpleado(Long idSemana, Long idEmpleado);
}
