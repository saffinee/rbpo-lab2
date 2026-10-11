package rbpo.task2.repository;

import org.springframework.stereotype.Repository;
import rbpo.task2.model.User;

@Repository
public class UserRepository extends MemoryRepository<User> {
}
