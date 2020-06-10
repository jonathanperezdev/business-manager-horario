package com.business.manager.horario.dao.entities;

import com.business.manager.horario.enums.ConceptoRecargoEnum;
import lombok.*;

import javax.persistence.*;

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

}