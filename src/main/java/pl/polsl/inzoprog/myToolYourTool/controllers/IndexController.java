package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.polsl.inzoprog.myToolYourTool.models.orm.User;
import pl.polsl.inzoprog.myToolYourTool.services.CategoryService;
import pl.polsl.inzoprog.myToolYourTool.services.LoginService;
import pl.polsl.inzoprog.myToolYourTool.services.OfferService;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class IndexController {

    private LoginService loginService;
    private CategoryService categoryService;
    private OfferService offerService;

    public IndexController(LoginService loginService, CategoryService categoryService, OfferService offerService) {
        this.loginService = loginService;
        this.categoryService = categoryService;
        this.offerService = offerService;
    }

    @RequestMapping(value = {"/", "/index"})
    public String homepage(Model model, HttpServletRequest request) {
        loginService.preparePath(model, request);
        model.addAttribute("parentCategories", categoryService.getParentCategories());

        User user = loginService.getLoggedUser(request.getCookies());
        if(user == null){
            model.addAttribute("offers", offerService.getLastActiveOffers());
        }else{
            model.addAttribute("offers", offerService.getLastestNeighbourhoodOffers(user.getPostalCode()));
        }

        return "index";
    }

}
