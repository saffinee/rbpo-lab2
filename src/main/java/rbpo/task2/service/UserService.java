package rbpo.task2.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rbpo.task2.model.User;
import rbpo.task2.repository.UserRepository;

import java.util.List;

@Service //сервис - тут вся логика, контроллер только принимает запрос и отдаёт ответ
public class UserService {
    private final UserRepository userRepository;
    private final VehicleService vehicleService;

    public UserService(UserRepository userRepository, VehicleService vehicleService) {
        this.userRepository = userRepository;
        this.vehicleService = vehicleService;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));
    }

    public User create(User user) {
        user.setId(null); //id выдаёт репозиторий
        return userRepository.save(user);
    }

    public User update(Long id, User request) {
        User user = getById(id);
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        return userRepository.save(user);
    }

    public void delete(Long id) {
        getById(id);
        vehicleService.deleteByOwnerId(id); //удаляем машины пользователя, чтобы не осталось ссылок на него
        userRepository.deleteById(id);
    }
}
