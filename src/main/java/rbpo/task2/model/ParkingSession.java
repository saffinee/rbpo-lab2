package rbpo.task2.model;

import java.time.LocalDateTime;

public class ParkingSession extends BaseEntity {
    private Long vehicleId;
    private Long zoneId;
    private LocalDateTime entryTime; //время въезда, ставит сервер
    private LocalDateTime exitTime; //время выезда, ставит сервер
    private Double amount; //сумма, считает сервер

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
