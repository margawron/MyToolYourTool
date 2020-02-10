package pl.polsl.inzoprog.myToolYourTool.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.polsl.inzoprog.myToolYourTool.models.forms.LoginForm;
import pl.polsl.inzoprog.myToolYourTool.models.forms.SearchForm;
import pl.polsl.inzoprog.myToolYourTool.models.orm.User;
import pl.polsl.inzoprog.myToolYourTool.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Service
public class LoginService {

    private UserRepository userRepository;
    private CryptoService cryptoService;

    public LoginService(UserRepository userRepository, CryptoService cryptoService){
        this.userRepository = userRepository;
        this.cryptoService = cryptoService;
    }

    public void preparePath(Model model, HttpServletRequest request){
        preparePage(model);
        prepareWelcomeMessagesForUser(model, request);
    }

    public void preparePage(Model model){
        // For search form
        model.addAttribute("searchForm", new SearchForm());
        // For login form
        model.addAttribute("loginForm", new LoginForm());
    }

    public void prepareWelcomeMessagesForUser(Model model, HttpServletRequest request){
        User loggedUser = getLoggedUser(request.getCookies());
        if(loggedUser != null){
            model.addAttribute("username", loggedUser.getName());
            model.addAttribute("time", DateTimeFormatter.ofPattern("HH:mm").format(LocalTime.now()));
        }
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

        if(cookies == null){
            return false;
        }

        String cookieUsername = null;
        String cookieToken = null;
        for(Cookie cookie: cookies){
            if(cookie.getName().equals("username")){
                cookieUsername = cookie.getValue();
            }else if(cookie.getName().equals("token")){
                cookieToken = cookie.getValue();
            }
        }
        // Nie znaleziono ciasteczka z nazwą użytkownika ani z tokenem
        if(cookieUsername == null || cookieToken == null){
            return false;
        }
        // Znajdź użytkownika
        User user = findUser(cookieUsername);
        // Taki użytkownik nie istnieje
        if(user == null){
            return false;
        }
        // Token od użytkownika nie jest taki sam jak w bazie danych
        if(user.getLoginToken() == null || !user.getLoginToken().equals(cookieToken)){
            return false;
        }
        // Token wygasł
        if(!LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).isBefore(user.getTokenExpiryDate())){
            return false;
        }
        return true;
    }

    public boolean loginWithConfirmation(LoginForm loginForm, HttpServletResponse response){
        Optional<User> userOpt = userRepository.findByName(loginForm.getUsername());
        if(!userOpt.isPresent()){
            return false;
        }
        User user = userOpt.get();
        String hashedAndSaltedPassword = cryptoService.digestPassAndSalt(loginForm.getPassword(), user.getPasswordSalt());
        // Sprawdź czy zhashowane hasło jest poprawne
        if(!user.getPassword().equals(hashedAndSaltedPassword)){
            return false;
        }
        Cookie usernameCookie = new Cookie("username", user.getName());
        String token = cryptoService.generateRandomAlphanumericString(24);
        Cookie tokenCookie = new Cookie("token", token);

        user.setLoginToken(token);
        user.setTokenExpiryDate(LocalDateTime.ofInstant(Instant.now(),ZoneOffset.UTC).plusDays(1));

        int expiryDate = 7*24*60*60;
        usernameCookie.setMaxAge(expiryDate);
        tokenCookie.setMaxAge(expiryDate);
        usernameCookie.setHttpOnly(true);
        tokenCookie.setHttpOnly(true);
        //NO HTTPS
//        usernameCookie.setSecure(true);
//        tokenCookie.setSecure(true);
        userRepository.save(user);
        response.addCookie(usernameCookie);
        response.addCookie(tokenCookie);
        return true;
    }

    public void logout(Cookie[] cookies){

        if(cookies == null){
            return;
        }
        String cookieUsername = null;
        String cookieToken = null;
        for(Cookie cookie: cookies){
            if(cookie.getName().equals("username")){
                cookieUsername = cookie.getValue();
            }else if(cookie.getName().equals("token")){
                cookieToken = cookie.getValue();
            }
        }
        if(cookieUsername == null || cookieToken == null){

        }

        Optional<User> userOptional = userRepository.findByName(cookieUsername);
        if(!userOptional.isPresent()){
            return;
        }

        User user = userOptional.get();


        user.setTokenExpiryDate(null);
        user.setLoginToken(null);
        userRepository.save(user);
    }

   public User getLoggedUser(Cookie[] cookies){
        if(cookies == null){
            return null;
        }
        String usernameCookie = null;
        String cookieToken = null;
        for(Cookie cookie: cookies){
            if(cookie.getName().equals("username")){
                usernameCookie = cookie.getValue();
            } else if(cookie.getName().equals("token")){
                cookieToken = cookie.getValue();
            }
        }
        Optional<User> optionalUser = userRepository.findByName(usernameCookie);
        if(!optionalUser.isPresent()){
            return null;
        }
        User user = optionalUser.get();
       // Taki użytkownik nie istnieje
       if(user == null){
           return null;
       }
       // Token od użytkownika nie jest taki sam jak w bazie danych
       if(user.getLoginToken() == null || !user.getLoginToken().equals(cookieToken)){
           return null;
       }
       // Token wygasł
       if(!LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).isBefore(user.getTokenExpiryDate())){
           return null;
       }
        return user;
   }

   public boolean checkUserPasswordById(Long id, String password){
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()){
            return false;
        }
        User user = optionalUser.get();
        String hashedAndSaltedPassword = cryptoService.digestPassAndSalt(password,user.getPasswordSalt());
        if(hashedAndSaltedPassword.equals(user.getPassword())){
            return true;
        }
        return false;

   }
}
