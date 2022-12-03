package ladakh.example.security.rest.dao;

import ladakh.example.security.rest.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private List<User> userList = new ArrayList<>();

    public UserRepository() {
        userList.add(new User("abc", "홍길동", 20));
        userList.add(new User("ab1", "하나", 21));
        userList.add(new User("ab2", "둘둘", 22));
        userList.add(new User("ab3", "삼삼", 23));
        userList.add(new User("ab4", "넷넷", 24));
        userList.add(new User("ab5", "다섯", 25));
    }

    public User getUser(String id) {
        Optional<User> user = userList.stream().findFirst().filter(u -> u.getId().equals(id));

        if (user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }
}
