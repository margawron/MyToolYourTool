package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.inzoprog.myToolYourTool.models.forms.LoginForm;
import pl.polsl.inzoprog.myToolYourTool.models.forms.SearchForm;


/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class LoginController {

    public LoginController(){

    }

    @RequestMapping(path = {"/login"}, method = RequestMethod.GET)
    public String login(Model model){

        model.addAttribute("searchForm", new SearchForm());
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }
}
