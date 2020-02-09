package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.inzoprog.myToolYourTool.models.forms.LoginForm;
import pl.polsl.inzoprog.myToolYourTool.models.forms.SearchForm;
import pl.polsl.inzoprog.myToolYourTool.services.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @RequestMapping(path = {"/login"}, method = RequestMethod.GET)
    public String loginPage(Model model, HttpServletRequest request){
        model.addAttribute("searchForm", new SearchForm());
        model.addAttribute("loginForm", new LoginForm());

        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return "login";
        }
        if(loginService.isUserLoggedIn(cookies)){
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping(path = {"/login"}, method = RequestMethod.POST)
    public String loginRequest(Model model, @ModelAttribute LoginForm loginForm, HttpServletResponse response){
        model.addAttribute("searchForm", new SearchForm());
        model.addAttribute("loginForm",new LoginForm());

        if(!loginService.loginWithConfirmation(loginForm, response)){
            model.addAttribute("message", "Nie można zalogować. Sprawdź czy podałeś poprawne dane");
            return "message";
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/logout")
    public String logout(Model model, HttpServletRequest request){
        model.addAttribute("searchForm", new SearchForm());
        model.addAttribute("loginForm", new LoginForm());

        loginService.logout(request.getCookies());

        return "redirect:/";
    }
}
