package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.inzoprog.myToolYourTool.models.forms.LoginForm;
import pl.polsl.inzoprog.myToolYourTool.models.forms.SearchForm;
import pl.polsl.inzoprog.myToolYourTool.services.LoginService;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class SearchController {

    private LoginService loginService;

    public SearchController(LoginService loginService){
        this.loginService = loginService;
    }

    @RequestMapping(path = {"/search"}, method = RequestMethod.GET)
    public String sendSearchPage(Model model, HttpServletRequest request){
        loginService.preparePath(model,request);

        // TODO implement

        return "search";
    }

    @RequestMapping(path = {"/search"}, method = RequestMethod.POST)
    public String search(Model model, HttpServletRequest request){
        loginService.preparePath(model,request);

        // TODO implement

        return "search";
    }
}
