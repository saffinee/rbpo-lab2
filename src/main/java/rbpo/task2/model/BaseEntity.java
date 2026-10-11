package rbpo.task2.model;

// общий класс для всех сущностей, чтобы у всех был id
public class BaseEntity {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
