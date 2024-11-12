package sysc4806.project24.mini_survey_monkey.repositories;

import org.springframework.data.repository.CrudRepository;
import sysc4806.project24.mini_survey_monkey.models.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer>{
    Optional<Role> findByAuthority(String authority);
}
