package pl.polsl.inzoprog.myToolYourTool.services;

import org.springframework.stereotype.Service;
import pl.polsl.inzoprog.myToolYourTool.models.forms.ProfileEditForm;
import pl.polsl.inzoprog.myToolYourTool.models.forms.RegisterForm;
import pl.polsl.inzoprog.myToolYourTool.models.orm.User;
import pl.polsl.inzoprog.myToolYourTool.repositories.UserRepository;
import pl.polsl.inzoprog.myToolYourTool.utils.Constants;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Service
public class RegisterService {

    private UserRepository userRepository;
    private CryptoService cryptoService;

    public RegisterService(UserRepository userRepository, CryptoService cryptoService) {
        this.userRepository = userRepository;
        this.cryptoService = cryptoService;
    }

    public boolean areRegisterCredentialsCorrect(String messageToBeReturned, RegisterForm registerForm) {
        String username = registerForm.getUsername();
        if (username.length() < 3) {
            messageToBeReturned = "Nazwa użytkownika zbyt krótka.";
            return false;
        }
        if (username.length() > 21) {
            messageToBeReturned = "Nazwa użytkownika zbyt długa.";
            return false;
        }
        String password = registerForm.getPasswordFirst();
        if (!password.matches("^([A-Za-z0-9]){3,20}$")) {
            messageToBeReturned = "Hasło powinno mieć od 3 do 20 znaków<br> oraz zawierać same litery oraz cyfry";
            return false;
        }

        if (!password.equals(registerForm.getPasswordSecond())) {
            messageToBeReturned = "Hasła nie są takie same.";
            return false;
        }

        if (!registerForm.getPostalCode().matches(Constants.postalCodeRegExp)) {
            messageToBeReturned = "Podany kod pocztowy nie jest prawidłowy.";
            return false;
        }

        if (!registerForm.getEmail().matches(Constants.emailRegExp)) {
            messageToBeReturned = "Mail nie jest poprawny";
            return false;
        }
        return true;
    }

    public User registerUser(RegisterForm registerForm) {
        User userBeingCreated = new User();
        userBeingCreated.setName(registerForm.getUsername());

        // Generate salt
        String salt = cryptoService.generateSalt();
        userBeingCreated.setPasswordSalt(salt);

        String password = registerForm.getPasswordFirst();
        String digestedPassword = cryptoService.digestPassAndSalt(password, salt);
        // Digest successful
        if (digestedPassword == null) {
            return null;
        }
        userBeingCreated.setPassword(digestedPassword);
        userBeingCreated.setCreatedAt(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
        userBeingCreated.setMail(registerForm.getEmail());
        Integer parsedPostalCode = parsePostalCode(registerForm.getPostalCode());
        userBeingCreated.setPostalCode(parsedPostalCode);

        User saved = userRepository.save(userBeingCreated);
        if (saved == null) {
            return null;
        }
        if (!saved.getName().equals(userBeingCreated.getName())) {
            return null;
        }

        return userBeingCreated;
    }

    public Integer parsePostalCode(String postalCode) {
        String[] splitted = postalCode.split("-");
        String postalCodeWithoutHyphen = splitted[0] + splitted[1];
        return Integer.parseInt(postalCodeWithoutHyphen);
    }

    public boolean updateUser(User loggedUser, ProfileEditForm editForm, String messageToBeReturned) {
        Optional<User> optionalUser = userRepository.findById(editForm.getId());
        if (!optionalUser.isPresent()) {
            messageToBeReturned = "Dostęp zabroniony.";
            return false;
        }
        User user = optionalUser.get();
        if (!user.getId().equals(loggedUser.getId())) {
            messageToBeReturned = "Dostęp zabroniony.";
            return false;
        }

        String hashedAndSaltedPassword = cryptoService.digestPassAndSalt(editForm.getCurrentPassword(), user.getPasswordSalt());
        if (!hashedAndSaltedPassword.equals(user.getPassword())) {
            messageToBeReturned = "Podane aktualne hasło jest nie poprawne";
            return false;
        }
        String password = editForm.getNewPasswordFirst();
        if (!password.matches("^([A-Za-z0-9]){3,20}$") && !password.equals("")) {
            messageToBeReturned = "Hasło powinno mieć od 3 do 20 znaków<br> oraz zawierać same litery oraz cyfry";
            return false;
        }
        if (!editForm.getNewPasswordFirst().equals(editForm.getNewPasswordSecond())) {
            messageToBeReturned = "Hasła nie są takie same.";
            return false;
        }

        if (!editForm.getPostalCode().matches(Constants.postalCodeRegExp)) {
            messageToBeReturned = "Podany kod pocztowy nie jest prawidłowy.";
            return false;
        }

        if (!editForm.getEmail().matches(Constants.emailRegExp)) {
            messageToBeReturned = "Mail nie jest poprawny";
            return false;
        }

        user.setPostalCode(parsePostalCode(editForm.getPostalCode()));
        user.setMail(editForm.getEmail());
        if (!editForm.getNewPasswordFirst().equals("")) {
            user.setPasswordSalt(cryptoService.generateSalt());
            user.setPassword(cryptoService.digestPassAndSalt(editForm.getNewPasswordFirst(), user.getPasswordSalt()));
        }
        userRepository.save(user);

        return true;
    }


}
