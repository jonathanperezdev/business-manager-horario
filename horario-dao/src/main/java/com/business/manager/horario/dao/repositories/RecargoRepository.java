package com.business.manager.horario.dao.repositories;

import com.business.manager.horario.dao.entities.DiaPago;
import com.business.manager.horario.dao.entities.Recargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface RecargoRepository extends JpaRepository<Recargo, Long> {
    @Query("SELECT sum(r.horas) "
            +"  FROM Recargo r, "
            +"       DiaPago d, "
            +"       SemanaPago s "
            +" WHERE r.diaPago.id = d.id "
            +"   AND d.idSemana = s.id "
            +"   AND d.fechaInicio < :fechaParam "
            +"   AND d.empleado.id = :idEmpleado ")
    Double findHorasAcumuladasSemanaPorEmpleado(@Param("fechaParam") LocalDateTime fecha,
                                                @Param("idEmpleado") Long idEmpleado);
}
