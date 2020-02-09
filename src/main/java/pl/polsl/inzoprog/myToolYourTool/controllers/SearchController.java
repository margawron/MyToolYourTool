package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.inzoprog.myToolYourTool.models.forms.SearchForm;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class SearchController {

    public SearchController(){
    }

    @RequestMapping(path = {"/search"}, method = RequestMethod.GET)
    public String sendSearchPage(Model model){

        // TODO implement

        model.addAttribute("searchForm",new SearchForm());
        return "search";
    }

    @RequestMapping(path = {"/search"}, method = RequestMethod.POST)
    public String search(Model model){

        // TODO implement

        return "search";
    }
}
