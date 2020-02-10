package pl.polsl.inzoprog.myToolYourTool.services;

import org.springframework.stereotype.Service;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Image;
import pl.polsl.inzoprog.myToolYourTool.repositories.ImageRepository;

import java.util.Optional;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Service
public class ImageService {

    private ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }

    public Image getImageById(Long id){
        Optional<Image> img =  imageRepository.findById(id);
        if(!img.isPresent()){
            return null;
        }
        return img.get();
    }

}
