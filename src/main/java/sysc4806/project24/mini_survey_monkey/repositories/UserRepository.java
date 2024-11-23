package sysc4806.project24.mini_survey_monkey.repositories;

import org.springframework.data.repository.CrudRepository;
import sysc4806.project24.mini_survey_monkey.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByUsername(String username);
}