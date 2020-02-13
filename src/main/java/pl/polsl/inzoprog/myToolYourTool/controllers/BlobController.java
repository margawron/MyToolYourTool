package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Image;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Offer;
import pl.polsl.inzoprog.myToolYourTool.models.orm.User;
import pl.polsl.inzoprog.myToolYourTool.services.ImageService;
import pl.polsl.inzoprog.myToolYourTool.services.LoginService;
import pl.polsl.inzoprog.myToolYourTool.services.OfferService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class BlobController {

    private ImageService imageService;
    private OfferService offerService;
    private LoginService loginService;

    public BlobController(ImageService imageService, OfferService offerService, LoginService loginService) {
        this.imageService = imageService;
        this.loginService = loginService;
        this.offerService = offerService;
    }

    @RequestMapping(path = "/image/get/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] sendSingleImage(@PathVariable(value = "id") final Long id) {
        if (id == null) {
            return null;
        }
        Image image = imageService.getImageById(id);
        if (image == null) {
            return null;
        }
        return image.getImageBytes();
    }

    @RequestMapping(path = "/image/upload/{offerId}", method = RequestMethod.POST)
    public String uploadSingleImage(Model model,
                                    HttpServletRequest request,
                                    @PathVariable(name = "offerId")final Long offerId,
                                    @RequestParam(name = "file") MultipartFile file
    ){
        loginService.preparePath(model, request);

        User loggedUser = loginService.getLoggedUser(request.getCookies());
        if(loggedUser == null){
            model.addAttribute("message", "Nie jesteś zalogowany");
            return "message";
        }
        Offer offerToWhichAddPhoto = offerService.getOfferById(offerId);
        if(offerToWhichAddPhoto == null){
            model.addAttribute("message", "Nie istnieje taka oferta");
            return "message";
        }

        if(!offerToWhichAddPhoto.getOwner().getId().equals(loggedUser.getId())){
            model.addAttribute("message", "Nie jesteś właścicielem tej oferty");
            return "message";
        }

        Image image = new Image();
        try {
            image.setImageBytes(file.getBytes());
        } catch (IOException e){
            model.addAttribute("message", "Wystąpił błąd podczas zapisywania obrazu");
            return "message";
        }
        image.setOriginOffer(offerToWhichAddPhoto);

        imageService.addImage(image);

        return "redirect:/offer/view/" + offerId;
    }
}
