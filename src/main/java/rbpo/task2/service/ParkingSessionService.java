package rbpo.task2.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rbpo.task2.model.ParkingSession;
import rbpo.task2.model.ParkingZone;
import rbpo.task2.repository.ParkingSessionRepository;
import rbpo.task2.repository.ParkingZoneRepository;
import rbpo.task2.repository.VehicleRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingSessionService {
    private final ParkingSessionRepository sessionRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingZoneRepository zoneRepository;

    public ParkingSessionService(ParkingSessionRepository sessionRepository, VehicleRepository vehicleRepository,
                                 ParkingZoneRepository zoneRepository) {
        this.sessionRepository = sessionRepository;
        this.vehicleRepository = vehicleRepository;
        this.zoneRepository = zoneRepository;
    }

    public List<ParkingSession> getAll() {
        return sessionRepository.findAll();
    }

    public ParkingSession getById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Парковочная сессия не найдена"));
    }

    // въезд: время въезда ставит сервер
    public ParkingSession create(ParkingSession session) {
        if (session.getVehicleId() == null || vehicleRepository.findById(session.getVehicleId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Автомобиль не найден");
        }
        if (session.getZoneId() == null || zoneRepository.findById(session.getZoneId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Парковочная зона не найдена");
        }
        session.setId(null);
        session.setEntryTime(LocalDateTime.now());
        session.setExitTime(null);
        session.setAmount(null);
        return sessionRepository.save(session);
    }

    // выезд: PATCH завершает сессию, время выезда и сумму считает сервер
    public ParkingSession finish(Long id) {
        ParkingSession session = getById(id);
        if (session.getExitTime() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Сессия уже завершена");
        }
        ParkingZone zone = zoneRepository.findById(session.getZoneId()).orElseThrow();

        session.setExitTime(LocalDateTime.now());
        long hours = Duration.between(session.getEntryTime(), session.getExitTime()).toHours() + 1; //начатый час считается как целый
        session.setAmount(hours * zone.getHourlyPrice());
        return sessionRepository.save(session);
    }

    public void delete(Long id) {
        getById(id);
        sessionRepository.deleteById(id);
    }
}
