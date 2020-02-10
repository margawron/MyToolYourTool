package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.polsl.inzoprog.myToolYourTool.services.CategoryService;
import pl.polsl.inzoprog.myToolYourTool.services.LoginService;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class IndexController {

    private LoginService loginService;
    private CategoryService categoryService;

    public IndexController(LoginService loginService, CategoryService categoryService){
        this.loginService = loginService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = {"/","/index"})
    public String homepage(Model model, HttpServletRequest request){
        loginService.preparePath(model,request);
        model.addAttribute("parentCategories", categoryService.getParentCategories());

        return "index";
    }

}
