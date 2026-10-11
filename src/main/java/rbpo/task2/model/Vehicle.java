package rbpo.task2.model;

public class Vehicle extends BaseEntity {
    private String number; //номер автомобиля
    private Long ownerId; //id владельца (User)

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
