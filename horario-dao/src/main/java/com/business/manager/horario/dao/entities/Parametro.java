package com.business.manager.horario.dao.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PARAMETRO")
public class Parametro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String nombre;

    @NonNull
    private String valor;
}