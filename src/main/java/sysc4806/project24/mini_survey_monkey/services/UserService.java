package sysc4806.project24.mini_survey_monkey.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sysc4806.project24.mini_survey_monkey.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("username not found"));
    }
}
