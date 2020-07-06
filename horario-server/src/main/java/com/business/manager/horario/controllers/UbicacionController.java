package com.business.manager.horario.controllers;

import com.business.manager.horario.model.UbicacionModel;
import com.business.manager.horario.services.UbicacionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/horario/v1/api/ubicacion")
@Slf4j
public class UbicacionController {

    @Autowired
    private UbicacionService ubicacionService;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    UbicacionModel getUbicacion(@PathVariable Integer id) {
        return ubicacionService.findByIdUbicacion(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    UbicacionModel updateUbicacion(@Valid @RequestBody UbicacionModel ubicacionModel) {
        return ubicacionService.updateUbicacion(ubicacionModel);
    }

    @DeleteMapping("/{idUbicacion}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    UbicacionModel deleteHorarioUbicacion(@PathVariable("idUbicacion") Integer idUbicacion) {
        return ubicacionService.deleteAllHorarioByUbicacion(idUbicacion);
    }

    @GetMapping("/ubicacionesSinHorario")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<UbicacionModel> findUbicacionesSinHorario() {
        return ubicacionService.findUbicacionesSinHorario();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<UbicacionModel> horarioUbicacionFindAll() {
        return ubicacionService.findAllHorarioUbicacion();
    }
}
