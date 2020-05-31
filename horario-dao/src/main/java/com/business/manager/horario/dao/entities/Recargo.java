package com.business.manager.horario.dao.entities;

import com.business.manager.horario.enums.ConceptoRecargoEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "RECARGO")
public class Recargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_dia")
    private DiaPago diaPago;

    private BigDecimal horas;

    @Enumerated(EnumType.STRING)
    private ConceptoRecargoEnum concepto;

    public void setConcepto(ConceptoRecargoEnum concepto) {
        this.concepto = concepto;
        this.orden = concepto.getOrden();
    }

    @Setter(AccessLevel.NONE)
    private Short orden;

    @Transient
    private Double lineaTiempo;

}