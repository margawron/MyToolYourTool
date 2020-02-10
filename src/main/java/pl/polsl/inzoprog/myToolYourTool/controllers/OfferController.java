package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Offer;
import pl.polsl.inzoprog.myToolYourTool.models.orm.User;
import pl.polsl.inzoprog.myToolYourTool.services.LoginService;
import pl.polsl.inzoprog.myToolYourTool.services.OfferService;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class OfferController {

    private OfferService offerService;
    private LoginService loginService;

    public OfferController(OfferService offerService, LoginService loginService){
        this.offerService = offerService;
        this.loginService = loginService;
    }

    @RequestMapping(path = "/offers/{offerId}", method = RequestMethod.GET)
    public String getSingleOffer(Model model, @PathVariable(value = "offerId")final Long id, HttpServletRequest request){
        loginService.preparePath(model, request);

        if(id == null){
            model.addAttribute("message", "Podano nie poprawny identyfikator oferty.");
            return "message";
        }

        Offer offer = offerService.getOfferById(id);
        if(offer == null){
            model.addAttribute("message", "Nie znaleziono oferty on identyfikatorze" + id +".");
            return "message";
        }

        User user = loginService.getLoggedUser(request.getCookies());

        if(user != null && user.getId().equals(offer.getOwner().getId())){
            model.addAttribute("offer", offer);
            return "offerOwnerView";
        }
        model.addAttribute("offer", offer);
        return "offerClientView";
    }

    @RequestMapping(path = "/offer/add", method = RequestMethod.GET)
    public String addOfferPage(Model model){
        loginService.preparePage(model);


        return "addOffer";
    }
}