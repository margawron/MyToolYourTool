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
public class ProfileController {

    public ProfileController(){
    }

    @RequestMapping(path = {"/profile"}, method = RequestMethod.GET)
    public String getProfile(Model model){
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("searchForm", new SearchForm());
        // TODO check if user is logged in
        // implement user lookup


        return "profile";
    }
}
