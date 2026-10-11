package rbpo.task2.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rbpo.task2.model.Vehicle;
import rbpo.task2.repository.ParkingPassRepository;
import rbpo.task2.repository.ParkingSessionRepository;
import rbpo.task2.repository.UserRepository;
import rbpo.task2.repository.VehicleRepository;

import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final ParkingSessionRepository sessionRepository;
    private final ParkingPassRepository passRepository;

    public VehicleService(VehicleRepository vehicleRepository, UserRepository userRepository,
                          ParkingSessionRepository sessionRepository, ParkingPassRepository passRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.passRepository = passRepository;
    }

    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle getById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Автомобиль не найден"));
    }

    public Vehicle create(Vehicle vehicle) {
        checkOwner(vehicle.getOwnerId());
        vehicle.setId(null);
        return vehicleRepository.save(vehicle);
    }

    public Vehicle update(Long id, Vehicle request) {
        Vehicle vehicle = getById(id);
        if (request.getNumber() != null) {
            vehicle.setNumber(request.getNumber());
        }
        if (request.getOwnerId() != null) {
            checkOwner(request.getOwnerId());
            vehicle.setOwnerId(request.getOwnerId());
        }
        return vehicleRepository.save(vehicle);
    }

    public void delete(Long id) {
        getById(id);
        deleteWithLinks(id);
    }

    // вызывается при удалении пользователя
    public void deleteByOwnerId(Long ownerId) {
        for (Vehicle vehicle : vehicleRepository.findByOwnerId(ownerId)) {
            deleteWithLinks(vehicle.getId());
        }
    }

    // вместе с машиной удаляем её сессии и абонементы
    private void deleteWithLinks(Long id) {
        sessionRepository.deleteByVehicleId(id);
        passRepository.deleteByVehicleId(id);
        vehicleRepository.deleteById(id);
    }

    private void checkOwner(Long ownerId) {
        if (ownerId == null || userRepository.findById(ownerId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Владелец не найден");
        }
    }
}
