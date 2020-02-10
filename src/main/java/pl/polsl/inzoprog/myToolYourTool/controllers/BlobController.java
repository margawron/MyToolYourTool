package pl.polsl.inzoprog.myToolYourTool.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Image;
import pl.polsl.inzoprog.myToolYourTool.services.ImageService;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Controller
public class BlobController {

    private ImageService imageService;

    public BlobController(ImageService imageService){
        this.imageService = imageService;
    }

    @RequestMapping(path = "/image/get/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] sendSingleImage(@PathVariable(value = "id")final Long id){
        if(id == null){
            return null;
        }
        Image image = imageService.getImageById(id);
        if(image == null){
            return null;
        }
        return image.getImageBytes();
    }
}
