package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.inzoprog.myToolYourTool.models.forms.EmailForm;
import pl.polsl.inzoprog.myToolYourTool.models.forms.RegisterForm;
import pl.polsl.inzoprog.myToolYourTool.models.orm.User;
import pl.polsl.inzoprog.myToolYourTool.services.LoginService;
import pl.polsl.inzoprog.myToolYourTool.services.RegisterService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class RegisterController {

    private RegisterService registerService;
    private LoginService loginService;

    public RegisterController(RegisterService registerService, LoginService loginService){
        this.registerService = registerService;
        this.loginService = loginService;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String registerUser(Model model, @ModelAttribute RegisterForm registerFormModel, HttpServletRequest request){
        loginService.preparePage(model);

        // Sprawdź czy nazwa użytkownika jest poprawna
        String message = "";
        if(!registerService.areRegisterCredentialsCorrect(message,registerFormModel)){
            model.addAttribute("message", message);
            return "message";
        }

        if(loginService.findUser(registerFormModel.getUsername()) != null){
            model.addAttribute("message", "Użytkownik o takiej nazwie już istnieje.");
            return "message";
        }

        User created =  registerService.registerUser(registerFormModel);
        if(created == null){
            model.addAttribute("message","Użytkownika NIE udało się utworzyć");
            return "message";
        }

        model.addAttribute("message", "Użytkownik o nazwie " + created.getName() +" został utworzony");
        return "message";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String registerPage(Model model, HttpServletRequest request){
        loginService.preparePage(model);
        model.addAttribute("registerFormModel", new RegisterForm());
        // Check if user is logged in
        if(loginService.isUserLoggedIn(request.getCookies())){
            return "redirect:/";
        }

        return "register";
    }

    @RequestMapping(path = "/forgottenPassword", method = RequestMethod.GET)
    public String forgottenPassword(Model model, @ModelAttribute RegisterForm registerForm, HttpServletRequest request){
        loginService.preparePath(model,request);
        model.addAttribute("emailForm", new EmailForm());

        return "passwordRecovery";
    }

    @RequestMapping(path = "/forgottenPassword", method = RequestMethod.POST)
    public String checkIfEmailExistAndSendEmail(Model model){
        loginService.preparePage(model);
        String response = "Jeżeli podany email istniał w bazie danych,<br> zostanie wysłany na niego email z instrukcjami<br>odzyskania hasła.";


        // TODO wyślij maila

        model.addAttribute("message",response);
        return "message";
    }

}
