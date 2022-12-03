package ladakh.example.security.rest;

import jakarta.servlet.http.HttpServletRequest;
import ladakh.example.security.rest.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CrudRest {

    @GetMapping("/api/demo")
    ResponseEntity getRest(HttpServletRequest request, @ModelAttribute User user) {
        printHeaders(request);
        log.info("Get -> {}", user);
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/api/demo", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity posdRestType(HttpServletRequest request, @RequestBody User user) {
        printHeaders(request);
        log.info("Post 2-> {}", user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/api/demo")
    ResponseEntity posdRest(HttpServletRequest request, @ModelAttribute User user) {
        printHeaders(request);
        log.info("Post -> {}", user);
        return ResponseEntity.ok(user);
    }
    @PutMapping(value = "/api/demo", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity putRestType(HttpServletRequest request, @RequestBody User user) {
        printHeaders(request);
        log.info("Put 2-> {}", user);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/api/demo")
    ResponseEntity putRest(HttpServletRequest request, @ModelAttribute User user) {
        printHeaders(request);
        log.info("Put -> {}", user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/api/demo")
    ResponseEntity delRest(HttpServletRequest request, @ModelAttribute User user) {
        printHeaders(request);
        log.info("Del -> {}", user);
        return ResponseEntity.ok(request.getParameterMap());
    }

    private void printHeaders(HttpServletRequest request) {
        request.getHeaderNames().asIterator().forEachRemaining(h -> {
            log.info("header: {} -> {}", h, request.getHeader(h));
        });
    }

    private void getBody(HttpServletRequest request) {

    }
}
