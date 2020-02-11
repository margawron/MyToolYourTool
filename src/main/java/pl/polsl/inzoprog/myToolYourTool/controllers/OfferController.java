package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.inzoprog.myToolYourTool.models.forms.AddOfferForm;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Category;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Offer;
import pl.polsl.inzoprog.myToolYourTool.models.orm.User;
import pl.polsl.inzoprog.myToolYourTool.services.CategoryService;
import pl.polsl.inzoprog.myToolYourTool.services.LoginService;
import pl.polsl.inzoprog.myToolYourTool.services.OfferService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class OfferController {

    private OfferService offerService;
    private LoginService loginService;
    private CategoryService categoryService;

    public OfferController(OfferService offerService, LoginService loginService, CategoryService categoryService){
        this.offerService = offerService;
        this.loginService = loginService;
        this.categoryService = categoryService;
    }

    @RequestMapping(path = "/offer/createFromCategory", method = RequestMethod.GET)
    public String createOfferFromCategory(Model model, HttpServletRequest request){
        loginService.preparePath(model,request);
        model.addAttribute("categories", categoryService.getParentCategories());

        return "createFromCategory";
    }

    @RequestMapping(path = "/offer/create/{categoryId}", method = RequestMethod.GET)
    public String createOfferPage(Model model, HttpServletRequest request, @PathVariable(name = "categoryId") final Long categoryId){
        loginService.preparePath(model, request);

        if(categoryId == null){
            model.addAttribute("message", "Identyfikator kategorii jest nie poprawny!");
            return "message";
        }

        Category toolCategory = categoryService.getCategoryById(categoryId);
        if(toolCategory == null){
            model.addAttribute("message", "Kategoria o takim identyfikatorze nie istnieje!");
            return "message";
        }

        model.addAttribute("category", toolCategory);
        model.addAttribute("addOfferForm", new AddOfferForm());

        return "addOffer";
    }

    @RequestMapping(path = "/offer/create/{categoryId}", method = RequestMethod.POST)
    public String createOfferRequest(Model model, HttpServletRequest request,@PathVariable(name = "categoryId")final Long categoryId ,@ModelAttribute AddOfferForm offerForm){

        loginService.preparePath(model, request);

        User loggedUser = loginService.getLoggedUser(request.getCookies());
        if(loggedUser == null){
            model.addAttribute("message", "Nie jesteś zalogowany żeby to zrobić!");
            return "message";
        }

        if(categoryId == null){
            model.addAttribute("message", "Numer kategorii jest nie poprawny");
            return "message";
        }
        // Jak coś takiego się dzieje to report do admina bo gościu się bawi
        // w edytorze html przeglądarki / szuka podatności

        Category itemCategory = categoryService.getCategoryById(categoryId);

        if(itemCategory == null){
            model.addAttribute("message", "Taka kategoria nie istnieje");
            return "message";
        }

        String outOptionalErrorMessage = "";
        if(!offerService.isCorrectForm(offerForm, outOptionalErrorMessage)){
            model.addAttribute("message",outOptionalErrorMessage);
            return "message";
        }

        Offer addedOffer = offerService.addOffer(loggedUser, offerForm, itemCategory);

        // TODO redirect to created offer
        return "redirect:/offer/view/" + addedOffer.getId().toString();
    }

    @RequestMapping(path = "/offer/view/{offerId}", method = RequestMethod.GET)
    public String getSingleOffer(Model model, @PathVariable(value = "offerId")final Long id, HttpServletRequest request){
        loginService.preparePath(model, request);

        if(id == null){
            model.addAttribute("message", "Podano nie poprawny identyfikator oferty.");
            return "message";
        }

        Offer offer = offerService.getOfferById(id);
        if(offer == null){
            model.addAttribute("message", "Nie znaleziono oferty on identyfikatorze " + id +".");
            return "message";
        }

        User user = loginService.getLoggedUser(request.getCookies());


        if(user != null && user.getId().equals(offer.getOwner().getId())){
            model.addAttribute("offer", offer);

            // TODO allow for photo uploading

            return "offerOwnerView";
        }
        // TODO make better template for viewing offers
        model.addAttribute("offer", offer);
        return "offerClientView";
    }


    @RequestMapping(path = "/offer/update/{offerId}", method = RequestMethod.POST)
    public String updateSingleOffer(Model model, @PathVariable(value = "offerId")final Long id, HttpServletRequest request){
        loginService.preparePath(model, request);
        // TODO implement
        throw new NotImplementedException();
    }



}
