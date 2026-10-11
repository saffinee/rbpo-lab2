package rbpo.task2.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rbpo.task2.model.ParkingPass;
import rbpo.task2.repository.ParkingPassRepository;
import rbpo.task2.repository.ParkingZoneRepository;
import rbpo.task2.repository.VehicleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingPassService {
    private final ParkingPassRepository passRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingZoneRepository zoneRepository;

    public ParkingPassService(ParkingPassRepository passRepository, VehicleRepository vehicleRepository,
                              ParkingZoneRepository zoneRepository) {
        this.passRepository = passRepository;
        this.vehicleRepository = vehicleRepository;
        this.zoneRepository = zoneRepository;
    }

    public List<ParkingPass> getAll() {
        return passRepository.findAll();
    }

    public ParkingPass getById(Long id) {
        return passRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Абонемент не найден"));
    }

    public ParkingPass create(ParkingPass pass) {
        if (pass.getVehicleId() == null || vehicleRepository.findById(pass.getVehicleId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Автомобиль не найден");
        }
        if (pass.getZoneId() == null || zoneRepository.findById(pass.getZoneId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Парковочная зона не найдена");
        }
        pass.setId(null);
        pass.setValidFrom(LocalDateTime.now()); //начало ставит сервер
        pass.setStatus("ACTIVE");
        return passRepository.save(pass);
    }

    public ParkingPass update(Long id, ParkingPass request) {
        ParkingPass pass = getById(id);
        if (request.getValidTo() != null) {
            pass.setValidTo(request.getValidTo());
        }
        if (request.getStatus() != null) {
            pass.setStatus(request.getStatus());
        }
        return passRepository.save(pass);
    }

    public void delete(Long id) {
        getById(id);
        passRepository.deleteById(id);
    }
}
