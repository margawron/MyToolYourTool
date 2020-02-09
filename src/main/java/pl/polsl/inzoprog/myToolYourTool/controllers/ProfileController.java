package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.inzoprog.myToolYourTool.models.forms.LoginForm;
import pl.polsl.inzoprog.myToolYourTool.models.forms.SearchForm;
import pl.polsl.inzoprog.myToolYourTool.services.LoginService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class ProfileController {

    private LoginService loginService;

    public ProfileController(LoginService loginService){
        this.loginService = loginService;
    }

    @RequestMapping(path = {"/profile"}, method = RequestMethod.GET)
    public String getProfile(Model model, HttpServletRequest request){
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("searchForm", new SearchForm());
        // check if user is logged in
        if(!loginService.isUserLoggedIn(request.getCookies())){
            return "redirect:/login";
        }
        // TODO instead of login from when logged in change to welcome message
        // implement user lookup


        return "profile";
    }
}
