package com.business.manager.horario.controllers;

import com.business.manager.horario.model.UbicacionModel;
import com.business.manager.horario.services.UbicacionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/horario/v1/api")
@Slf4j
public class UbicacionController {

    @Autowired
    private UbicacionService ubicacionService;

    @GetMapping("/ubicacion/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    UbicacionModel getUbicacion(@PathVariable Integer id) {
        return ubicacionService.findByIdUbicacion(id);
    }

    @GetMapping("/ubicacion/horarioDefault/{idUbicacion}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    UbicacionModel ubicacionHorarioDefaultByIdUbicacion(@PathVariable("idUbicacion") Integer idUbicacion) {
        return ubicacionService.getUbicacionHorarioDefault(idUbicacion);
    }

    @PutMapping("/ubicacion")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    UbicacionModel updateUbicacion(@Valid @RequestBody UbicacionModel ubicacionModel) {
        return ubicacionService.updateUbicacion(ubicacionModel);
    }

    @DeleteMapping("/ubicacion/horario/{idUbicacion}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    UbicacionModel deleteHorarioUbicacion(@PathVariable("idUbicacion") Integer idUbicacion) {
        return ubicacionService.deleteAllHorarioByUbicacion(idUbicacion);
    }
}
