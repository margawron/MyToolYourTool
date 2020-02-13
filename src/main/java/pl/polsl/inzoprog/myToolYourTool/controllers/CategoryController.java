package pl.polsl.inzoprog.myToolYourTool.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Offer;
import pl.polsl.inzoprog.myToolYourTool.services.CategoryService;
import pl.polsl.inzoprog.myToolYourTool.services.LoginService;
import pl.polsl.inzoprog.myToolYourTool.services.OfferService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class CategoryController {

    private CategoryService categoryService;
    private OfferService offerService;
    private LoginService loginService;

    public CategoryController(CategoryService categoryService, OfferService offerService, LoginService loginService){
        this.categoryService = categoryService;
        this.offerService = offerService;
        this.loginService = loginService;
    }

    @RequestMapping(path = "/category/view/{catId}", method = RequestMethod.GET)
    public String showOffersFromSingleCategory(Model model, HttpServletRequest request, @PathVariable(name = "catId") final Long categoryId){
        loginService.preparePath(model, request);

        if(categoryId == null){
            model.addAttribute("message", "Taka kategoria nie istnieje!");
        }

        List<Offer> offerList = offerService.getOfferFromCategory(categoryId);
        model.addAttribute("offers", offerList);
        return "results";
    }

}
