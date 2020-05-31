CREATE TABLE PERIODO_PAGO (
  id bigserial primary key,
  fecha_inicio date NOT NULL,
  fecha_fin date NOT NULL,
  estado_pago VARCHAR(20)
);

CREATE TABLE SEMANA_PAGO (
  id bigserial primary key,
  fecha_inicio date NOT NULL,
  fecha_fin date NOT NULL,
  numero_semana smallint NOT NULL,
  id_periodo bigint NOT NULL,
  CONSTRAINT HORARIO_SEMANA_idPeriodo_PERIODO_PAGO_id FOREIGN KEY (id_periodo) REFERENCES periodo_pago(id)
) ;

CREATE TABLE UBICACION (
  id serial primary key,
  nombre varchar(30) DEFAULT null,
  UNIQUE(nombre)
) ;

CREATE TABLE FESTIVO (
  id serial primary key,
  festivo date NOT NULL,
  UNIQUE(festivo)
) ;

CREATE TABLE EMPLEADO (
  id bigserial primary key,
  nombres varchar(30) NOT NULL,
  apellidos varchar(30) NOT NULL
) ;

CREATE TABLE DIA_PAGO (
  id bigserial primary key,
  id_semana bigint NOT NULL,
  id_ubicacion INT not NULL,
  fecha_inicio timestamp NOT NULL,
  fecha_fin timestamp NOT NULL,
  id_empleado bigint NOT NULL,
  CONSTRAINT DIA_PAGO_idSemana_SEMANA_PAGO_id FOREIGN KEY (id_semana) REFERENCES semana_pago(id),
  CONSTRAINT DIA_PAGO_idUbicacion_UBICACION_id FOREIGN KEY (id_ubicacion) REFERENCES UBICACION(id),
  CONSTRAINT DIA_PAGO_idEmpleado_EMPLEADO_id FOREIGN KEY (id_empleado) REFERENCES EMPLEADO(id)
) ;

CREATE TABLE RECARGO (
  id bigserial primary key,
  id_dia bigint NOT NULL,
  horas numeric(5,1) NOT NULL,
  concepto varchar(25) NOT NULL,
  orden smallint NOT null,
  UNIQUE(concepto,id_dia),
  CONSTRAINT RECARGO_idDia_DIA_PAGO_id FOREIGN KEY (id_dia) REFERENCES DIA_PAGO(id)
) ;

 CREATE TABLE PARAMETRO (
  id serial primary key,
  nombre varchar(25) NOT NULL,
  valor varchar(30) NOT NULL,
  UNIQUE (nombre)
  ) ;

CREATE TABLE HORARIO_UBICACION (
  id serial primary key,
  id_ubicacion INT NOT NULL,
  dia varchar(10) NOT NULL,
  hora_inicio time NOT NULL,
  hora_fin time NOT NULL,
  orden smallint NOT NULL,
  UNIQUE(id_ubicacion,dia),
  CONSTRAINT HORARIO_UBICACION_idUbicacion_UBICACION_id FOREIGN KEY (id_ubicacion) REFERENCES ubicacion (id)
) ;