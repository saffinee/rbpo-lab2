package rbpo.task2.repository;

import org.springframework.stereotype.Repository;
import rbpo.task2.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VehicleRepository extends MemoryRepository<Vehicle> {

    public List<Vehicle> findByOwnerId(Long ownerId) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle vehicle : findAll()) {
            if (vehicle.getOwnerId().equals(ownerId)) {
                result.add(vehicle);
            }
        }
        return result;
    }
}
