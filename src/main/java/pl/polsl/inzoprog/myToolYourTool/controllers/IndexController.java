package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.polsl.inzoprog.myToolYourTool.models.User;
import pl.polsl.inzoprog.myToolYourTool.repositories.UserRepository;

import java.util.List;

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
        model.addAttribute("tekst", "eloelo320");
        List<User> uzytkownicy = userRepository.findAll();
        if(uzytkownicy.size() == 0){
            model.addAttribute("uzytkownicy", true);
        }


        return "index";
    }

}
