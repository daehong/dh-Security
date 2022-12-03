package ladakh.example.security.rest;

import ladakh.example.security.auth.JwtTokenProvider;
import ladakh.example.security.rest.model.User;
import ladakh.example.security.rest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginRest {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        log.info("req login: {}", user);
        String jwt = null;

        if (user != null && !user.getId().isBlank()) {

            User userDetails = userService.getUser(user.getId());

            if (userDetails != null) {
                jwt = jwtTokenProvider.generateToken(userDetails);
            }
        }
        return ResponseEntity.ok(jwt);
    }

}
