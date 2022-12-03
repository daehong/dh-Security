package ladakh.example.security.rest.service;

import ladakh.example.security.rest.dao.UserRepository;
import ladakh.example.security.rest.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       log.info("user detail: {}", username);
        return userRepository.getUser(username);
    }

    public User getUser(String id) {
        return userRepository.getUser(id);
    }
}
