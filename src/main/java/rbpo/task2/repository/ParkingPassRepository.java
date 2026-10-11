package rbpo.task2.repository;

import org.springframework.stereotype.Repository;
import rbpo.task2.model.ParkingPass;

@Repository
public class ParkingPassRepository extends MemoryRepository<ParkingPass> {

    public void deleteByVehicleId(Long vehicleId) {
        for (ParkingPass pass : findAll()) {
            if (pass.getVehicleId().equals(vehicleId)) {
                deleteById(pass.getId());
            }
        }
    }

    public void deleteByZoneId(Long zoneId) {
        for (ParkingPass pass : findAll()) {
            if (pass.getZoneId().equals(zoneId)) {
                deleteById(pass.getId());
            }
        }
    }
}
