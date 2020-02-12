package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.inzoprog.myToolYourTool.models.forms.ProfileEditForm;
import pl.polsl.inzoprog.myToolYourTool.models.orm.User;
import pl.polsl.inzoprog.myToolYourTool.services.LoginService;
import pl.polsl.inzoprog.myToolYourTool.services.RegisterService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class ProfileController {

    private LoginService loginService;
    private RegisterService registerService;

    public ProfileController(LoginService loginService, RegisterService registerService) {
        this.loginService = loginService;
        this.registerService = registerService;
    }

    @RequestMapping(path = {"/profile"}, method = RequestMethod.GET)
    public String getUserProfile(Model model, HttpServletRequest request) {
        if (!loginService.isUserLoggedIn(request.getCookies())) {
            return "redirect:/login";
        }

        loginService.preparePath(model, request);

        User user = loginService.getLoggedUser(request.getCookies());


        ProfileEditForm pef = new ProfileEditForm();
        pef.setId(user.getId());
        pef.setCreatedAt(user.getCreatedAt());
        pef.setEmail(user.getMail());
        pef.setUsername(user.getName());

        StringBuilder sb = new StringBuilder();
        sb.append(user.getPostalCode().toString());
        sb.insert(2, '-');

        pef.setPostalCode(sb.toString());
        model.addAttribute("profileEditForm", pef);
        return "profile";
    }

    @RequestMapping(path = "/updateProfile", method = RequestMethod.POST)
    public String updateProfile(Model model, @ModelAttribute ProfileEditForm editForm, HttpServletRequest request) {
        loginService.preparePage(model);

        User loggedUser = loginService.getLoggedUser(request.getCookies());
        String returnMessage = "";
        if (!registerService.updateUser(loggedUser, editForm, returnMessage)) {
            model.addAttribute("message", returnMessage);
            return "message";
        }

        return "redirect:profile";
    }
}
