package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.inzoprog.myToolYourTool.models.forms.RegisterForm;
import pl.polsl.inzoprog.myToolYourTool.models.forms.SearchForm;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Offer;
import pl.polsl.inzoprog.myToolYourTool.services.LoginService;
import pl.polsl.inzoprog.myToolYourTool.services.SearchService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class SearchController {

    private LoginService loginService;
    private SearchService searchService;


    public SearchController(LoginService loginService, SearchService searchService) {
        this.loginService = loginService;
        this.searchService = searchService;
    }

    @RequestMapping(path = {"/search"}, method = RequestMethod.GET)
    public String sendSearchPage(Model model, HttpServletRequest request) {
        loginService.preparePath(model, request);


        return "search";
    }

    @RequestMapping(path = {"/search"}, method = RequestMethod.POST)
    public String search(Model model, HttpServletRequest request, @ModelAttribute SearchForm searchForm) {
        loginService.preparePath(model, request);
        List<Offer> offerList = searchService.getResults(searchForm);
        model.addAttribute("offers", offerList);

        return "results";
    }
}
