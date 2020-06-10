package com.business.manager.horario.dao.entities;

import java.time.LocalTime;

import com.business.manager.horario.enums.DiaEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(of = "dia")
@NoArgsConstructor
@Entity
@Table(name = "HORARIO_UBICACION")
public class HorarioUbicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_ubicacion")
    private Ubicacion ubicacion;

    @NonNull
    @Enumerated(EnumType.STRING)
    private DiaEnum dia;

    @NonNull
    private LocalTime horaInicio;

    @NonNull
    private LocalTime horaFin;

    @NonNull
    private Short orden;
}


