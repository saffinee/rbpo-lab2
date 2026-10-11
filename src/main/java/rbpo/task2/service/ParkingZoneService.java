package rbpo.task2.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rbpo.task2.model.ParkingZone;
import rbpo.task2.repository.ParkingPassRepository;
import rbpo.task2.repository.ParkingSessionRepository;
import rbpo.task2.repository.ParkingZoneRepository;

import java.util.List;

@Service
public class ParkingZoneService {
    private final ParkingZoneRepository zoneRepository;
    private final ParkingSessionRepository sessionRepository;
    private final ParkingPassRepository passRepository;

    public ParkingZoneService(ParkingZoneRepository zoneRepository, ParkingSessionRepository sessionRepository,
                              ParkingPassRepository passRepository) {
        this.zoneRepository = zoneRepository;
        this.sessionRepository = sessionRepository;
        this.passRepository = passRepository;
    }

    public List<ParkingZone> getAll() {
        return zoneRepository.findAll();
    }

    public ParkingZone getById(Long id) {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Парковочная зона не найдена"));
    }

    public ParkingZone create(ParkingZone zone) {
        zone.setId(null);
        return zoneRepository.save(zone);
    }

    public ParkingZone update(Long id, ParkingZone request) {
        ParkingZone zone = getById(id);
        if (request.getCapacity() != null) {
            zone.setCapacity(request.getCapacity());
        }
        if (request.getHourlyPrice() != null) {
            zone.setHourlyPrice(request.getHourlyPrice());
        }
        return zoneRepository.save(zone);
    }

    public void delete(Long id) {
        getById(id);
        sessionRepository.deleteByZoneId(id); //удаляем сессии и абонементы этой зоны
        passRepository.deleteByZoneId(id);
        zoneRepository.deleteById(id);
    }
}
