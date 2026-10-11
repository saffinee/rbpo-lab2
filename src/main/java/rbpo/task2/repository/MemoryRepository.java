package rbpo.task2.repository;

import rbpo.task2.model.BaseEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// хранилище в памяти: данные лежат в Map, базы данных нет
// из этого класса наследуются все остальные репозитории
public abstract class MemoryRepository<T extends BaseEntity> {
    private final Map<Long, T> items = new LinkedHashMap<>(); //id -> объект
    private long nextId = 1;

    public List<T> findAll() {
        return new ArrayList<>(items.values());
    }

    public Optional<T> findById(Long id) {
        return Optional.ofNullable(items.get(id));
    }

    public T save(T item) {
        if (item.getId() == null) { //новый объект - выдаём ему id
            item.setId(nextId);
            nextId++;
        }
        items.put(item.getId(), item);
        return item;
    }

    public void deleteById(Long id) {
        items.remove(id);
    }
}
