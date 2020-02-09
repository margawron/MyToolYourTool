package pl.polsl.inzoprog.myToolYourTool.services;

import org.springframework.stereotype.Service;
import pl.polsl.inzoprog.myToolYourTool.models.orm.User;
import pl.polsl.inzoprog.myToolYourTool.repositories.UserRepository;

import javax.servlet.http.Cookie;
import java.util.Optional;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Service
public class LoginService {

    private UserRepository userRepository;

    public LoginService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findUser(String username){
        Optional<User> user = userRepository.findByName(username);
        if(!user.isPresent()){
            return null;
        }
        if(!user.get().getName().equals(username)){
            return null;
        }
        return user.get();
    }

    public boolean isUserLoggedIn(Cookie[] cookies){
        String cookieUsername = null;
        String cookieToken = null;

        return false;
    }
}
