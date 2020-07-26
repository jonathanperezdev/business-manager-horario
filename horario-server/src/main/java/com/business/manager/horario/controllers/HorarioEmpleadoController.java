package com.business.manager.horario.controllers;

import com.business.manager.horario.model.empleado.HorarioEmpleadoModel;
import com.business.manager.horario.services.DiaPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/horario/v1/api/empleado")
public class HorarioEmpleadoController {

    @Autowired
    private DiaPagoService diaPagoService;

    @GetMapping("/semana/{idSemana}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<HorarioEmpleadoModel> getHorarioEmpleadoBySemanaPago(@PathVariable("idSemana") Long idSemana) {
        return diaPagoService.getHorarioEmpleadoBySemana(idSemana);
    }

    @GetMapping("/all/semana/{idSemana}/ubicacion/{idUbicacion}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<HorarioEmpleadoModel> getHorarioEmpleadoBySemanaPago(
            @PathVariable("idSemana") Long idSemana,
            @PathVariable("idUbicacion") Integer idUbicacion) {
        return diaPagoService.getHorarioEmpleadoBySemanaAndUbicacion(idSemana, idUbicacion);
    }

    @GetMapping("/{idEmpleado}/semana/{idSemana}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    HorarioEmpleadoModel getHorarioEmpleado(
            @PathVariable("idSemana") Long idSemana,
            @PathVariable("idEmpleado") Long idEmpleado) {
        return diaPagoService.getHorarioEmpleado(idSemana, idEmpleado);
    }

    @PutMapping("/{idEmpleado}/semana/{idSemana}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    HorarioEmpleadoModel updateHorarioEmpleado(@RequestBody HorarioEmpleadoModel horarioEmpleadoModel) {
        return diaPagoService.updateHorarioEmpleado(horarioEmpleadoModel);
    }
}
