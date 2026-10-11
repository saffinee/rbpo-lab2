package rbpo.task2.repository;

import org.springframework.stereotype.Repository;
import rbpo.task2.model.ParkingSession;

@Repository
public class ParkingSessionRepository extends MemoryRepository<ParkingSession> {

    public void deleteByVehicleId(Long vehicleId) {
        for (ParkingSession session : findAll()) {
            if (session.getVehicleId().equals(vehicleId)) {
                deleteById(session.getId());
            }
        }
    }

    public void deleteByZoneId(Long zoneId) {
        for (ParkingSession session : findAll()) {
            if (session.getZoneId().equals(zoneId)) {
                deleteById(session.getId());
            }
        }
    }
}
