package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.polsl.inzoprog.myToolYourTool.models.forms.LoginForm;
import pl.polsl.inzoprog.myToolYourTool.models.forms.SearchForm;
import pl.polsl.inzoprog.myToolYourTool.repositories.UserRepository;


/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class IndexController {

    private UserRepository userRepository;

    public IndexController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @RequestMapping(value = {"/","/index"})
    public String homepage(Model model){
        // For search form
        model.addAttribute("searchForm", new SearchForm());
        // For login form
        model.addAttribute("loginForm", new LoginForm());

        // TOOD check if user is logged in, and give him necessary priviliages



        return "index";
    }

}
