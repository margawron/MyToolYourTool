package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class RegisterController {

    public RegisterController(){
    }

    @RequestMapping(value = {"/register", })
    public String registerPage(Model model){

        return "register";
    }
}
