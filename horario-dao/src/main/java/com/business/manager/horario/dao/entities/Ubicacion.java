package com.business.manager.horario.dao.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "UBICACION")
public class Ubicacion {
    @Id
    @NonNull
    private Integer id;

    @NonNull
    private String nombre;

    @OneToMany(
            mappedBy = "ubicacion",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private Set<HorarioUbicacion> horariosByUbicacion = new HashSet();
}

