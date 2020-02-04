package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.inzoprog.myToolYourTool.models.forms.RegisterForm;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class RegisterController {

    public RegisterController(){
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String registerPage(Model model, @ModelAttribute RegisterForm registerFormModel){
        /* TODO
            czy jego dane są poprawne sprawdzić czy użytkownik
            jest już zalogowany(ciasteczka) oraz czy istnieje w bazie danych
            jeżeli wszystko jest ok to dodaj użytkownika do bazy danych i przekieruj do strony z logowaniem
        */

        return "redirect:index";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String registerRequest(Model model){


        // obiekt dla obsłużanego formularza do wypełnienia przez kontekst Springa(Thymeleaf'a)
        model.addAttribute("registerFormModel", new RegisterForm());
        return "register";
    }

}
