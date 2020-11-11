package com.business.manager.horario.dao.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "EMPLEADO")
public class Empleado {
    @Id
    @NonNull
    private Long id;

    @NonNull
    private String nombres;
    
    @NonNull
    private String apellidos;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_ubicacion")
    private Ubicacion ubicacion;
}

