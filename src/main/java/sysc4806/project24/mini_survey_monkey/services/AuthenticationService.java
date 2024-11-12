package sysc4806.project24.mini_survey_monkey.services;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sysc4806.project24.mini_survey_monkey.models.Role;
import sysc4806.project24.mini_survey_monkey.models.User;
import sysc4806.project24.mini_survey_monkey.repositories.RoleRepository;
import sysc4806.project24.mini_survey_monkey.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String password){
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        return userRepository.save(new User(username,encodedPassword,authorities));
    }
}
