package pl.polsl.inzoprog.myToolYourTool.services;

import org.springframework.stereotype.Service;
import pl.polsl.inzoprog.myToolYourTool.models.forms.RegisterForm;
import pl.polsl.inzoprog.myToolYourTool.models.orm.User;
import pl.polsl.inzoprog.myToolYourTool.repositories.UserRepository;
import pl.polsl.inzoprog.myToolYourTool.utils.Constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.Random;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Service
public class RegisterService {

    private UserRepository userRepository;

    public RegisterService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean areRegisterCredentialsCorrect(String messageToBeReturned, RegisterForm registerForm){
        String username = registerForm.getUsername();
        if(username.length()<3){
            messageToBeReturned= "Nazwa użytkownika zbyt krótka.";
            return false;
        }
        if(username.length()>21){
            messageToBeReturned = "Nazwa użytkownika zbyt długa.";
            return false;
        }
        String password = registerForm.getPasswordFirst();
        if(!password.matches("^([A-Za-z0-9]){3,20}$")){
            messageToBeReturned = "Hasło powinno mieć od 3 do 20 znaków<br> oraz zawierać same litery oraz cyfry";
            return false;
        }

        if(!password.equals(registerForm.getPasswordSecond())){
            messageToBeReturned = "Hasła nie są takie same.";
            return false;
        }

        if(!registerForm.getPostalCode().matches(Constants.postalCodeRegExp)){
            messageToBeReturned = "Podany kod pocztowy nie jest prawidłowy.";
            return false;
        }

        if(!registerForm.getEmail().matches(Constants.emailRegExp)){
            messageToBeReturned = "Mail nie jest poprawny";
            return false;
        }
        return true;
    }

    public User registerUser(RegisterForm registerForm){
        User userBeingCreated = new User();
        userBeingCreated.setName(registerForm.getUsername());

        // Generate salt
        byte[] rawSalt = new byte[14];
        new Random().nextBytes(rawSalt);
        String salt = new String(rawSalt, StandardCharsets.UTF_8);
        userBeingCreated.setPasswordSalt(salt);

        String password = registerForm.getPasswordFirst();
        String digestedPassword = digestPassAndSalt(password, salt);
        // Digest successful
        if(digestedPassword == null){
            return null;
        }
        userBeingCreated.setPassword(digestedPassword);
        userBeingCreated.setCreatedAt(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
        userBeingCreated.setMail(registerForm.getEmail());
        Integer parsedPostalCode = parsePostalCode(registerForm.getPostalCode());
        userBeingCreated.setPostalCode(parsedPostalCode);

        User saved = userRepository.save(userBeingCreated);
        if(saved == null){
            return null;
        }
        if(!saved.getName().equals(userBeingCreated.getName())){
            return null;
        }

        return userBeingCreated;
    }

    public String digestPassAndSalt(String password, String salt){
        String toBeDigested = password + salt;

        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e){
            return null;
        }
        byte[] digested = md.digest(toBeDigested.getBytes());

        return new String(digested, StandardCharsets.UTF_8);
    }

    public Integer parsePostalCode(String postalCode){
        String[] splitted = postalCode.split("-");
        String postalCodeWithoutHyphen = splitted[0] + splitted[1];
        return Integer.parseInt(postalCodeWithoutHyphen);
    }



}
