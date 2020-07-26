package com.business.manager.horario.dao.entities;

import com.business.manager.horario.enums.ConceptoRecargoEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = {"concepto"})
@Table(name = "RECARGO")
public class Recargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_dia")
    private DiaPago diaPago;

    private Double horas;

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

    @Override
    public String toString(){
        return this.horas+" - "+this.concepto;
    }

}