package sysc4806.project24.mini_survey_monkey;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import sysc4806.project24.mini_survey_monkey.models.Role;
import sysc4806.project24.mini_survey_monkey.models.User;
import sysc4806.project24.mini_survey_monkey.repositories.RoleRepository;
import sysc4806.project24.mini_survey_monkey.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class MiniSurveyMonkeyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniSurveyMonkeyApplication.class, args);
	}

	@Bean
	public CommandLineRunner initRoles(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByAuthority("USER").isEmpty()) {
				roleRepository.save(new Role("USER"));
			}
		};
	}

}
