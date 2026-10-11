package rbpo.task2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rbpo.task2.model.ParkingPass;
import rbpo.task2.service.ParkingPassService;

import java.util.List;

@RestController //спринг находит этот класс при запуске и запоминает адреса
@RequestMapping("/api/parking-passes")
public class ParkingPassController {
    private final ParkingPassService service;

    public ParkingPassController(ParkingPassService service) {
        this.service = service;
    }

    @GetMapping
    public List<ParkingPass> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ParkingPass getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //201
    public ParkingPass create(@RequestBody ParkingPass request) {
        return service.create(request);
    }

    @PatchMapping("/{id}")
    public ParkingPass update(@PathVariable Long id, @RequestBody ParkingPass request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
