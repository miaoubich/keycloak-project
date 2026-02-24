package com.miaoubich.param;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/param")
public class ParamController {

    private final ParamService paramService;

    public ParamController(ParamService paramService) {
        this.paramService = paramService;
    }

    @PostMapping
    public ResponseEntity<Param> saveParamValue(@RequestBody Param param) {
        paramService.saveParamValue(param);
        return ResponseEntity.ok(param);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Param> getParamValue(@PathVariable Long id) {
        Param parameter = paramService.getParamById(id);
        return ResponseEntity.ok(parameter);
    }

    @PutMapping
    public ResponseEntity<Param> updateParamValue(@RequestBody Param param) {
        return ResponseEntity.ok(paramService.updateParamValue(param));
    }
}
