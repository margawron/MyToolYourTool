package pl.polsl.inzoprog.myToolYourTool.services;

import org.springframework.stereotype.Service;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Offer;
import pl.polsl.inzoprog.myToolYourTool.repositories.OfferRepository;

import java.util.Optional;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Service
public class OfferService {

    private OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository){
        this.offerRepository = offerRepository;
    }

    public Offer getOfferById(Long id){
        Optional<Offer> offerOptional = offerRepository.findById(id);
        if(!offerOptional.isPresent()){
            return null;
        }
        return offerOptional.get();
    }
}
