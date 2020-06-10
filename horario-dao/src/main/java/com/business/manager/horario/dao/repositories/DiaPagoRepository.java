package com.business.manager.horario.dao.repositories;

import com.business.manager.horario.dao.entities.DiaPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaPagoRepository extends JpaRepository<DiaPago, Long> {
}
