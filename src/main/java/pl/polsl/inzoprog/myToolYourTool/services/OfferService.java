package pl.polsl.inzoprog.myToolYourTool.services;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import pl.polsl.inzoprog.myToolYourTool.models.forms.AddOfferForm;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Category;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Image;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Offer;
import pl.polsl.inzoprog.myToolYourTool.models.orm.User;
import pl.polsl.inzoprog.myToolYourTool.repositories.ImageRepository;
import pl.polsl.inzoprog.myToolYourTool.repositories.OfferRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Service
public class OfferService {

    private OfferRepository offerRepository;
    private EntityManagerFactory entityManagerFactory;
    private ImageRepository imageRepository;

    public OfferService(OfferRepository offerRepository,ImageRepository imageRepository, EntityManagerFactory entityManagerFactory) {
        this.offerRepository = offerRepository;
        this.entityManagerFactory = entityManagerFactory;
        this.imageRepository = imageRepository;
    }

    public Offer getOfferById(Long id) {
        Optional<Offer> offerOptional = offerRepository.findById(id);
        if (!offerOptional.isPresent()) {
            return null;
        }
        return offerOptional.get();
    }

    public List<Offer> getLastActiveOffers(){
        return offerRepository.findTop20ByIsActiveIsTrue();
    }

    public List<Offer> getLastestNeighbourhoodOffers(Integer postalCode){
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Offer> typedQuery = em.createQuery("select o from Offer o where o.isActive = true and CONCAT(o.owner.postalCode,'') like concat(:postalCode,'') ORDER BY o.id", Offer.class);
        typedQuery.setMaxResults(20);
        typedQuery.setParameter("postalCode", postalCode);
        List<Offer> resultList = typedQuery.getResultList();
        for(Offer result : resultList){
            List<Image> images = getSingleImageOfOffer(result.getId());
            result.setOfferImages(images);
        }
        return resultList;
    }

    public List<Image> getSingleImageOfOffer(Long id){
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Image> typedQuery = em.createQuery(    "select i from Image i where i.originOffer.id = :offerId", Image.class);
        typedQuery.setMaxResults(1);
        typedQuery.setParameter("offerId", id);
        return typedQuery.getResultList();
    }

    public List<Offer> getUserOffers(Long id){
        List<Offer> offerList = offerRepository.getOfferByOwnerId(id);
        for(Offer offer : offerList){
            List<Image> images = getSingleImageOfOffer(offer.getId());
            offer.setOfferImages(images);
        }
        return offerList;
    }

    public List<Offer> getOfferFromCategory(Long categoryId){
        List<Offer> offerList = offerRepository.findTop20ByCategoryId(categoryId);
        for(Offer result : offerList){
            List<Image> images = getSingleImageOfOffer(result.getId());
            result.setOfferImages(images);
        }
        return offerList;
    }

    public List<Image> getOfferImages(Long offerId){
        return imageRepository.findAllByOriginOfferId(offerId);
    }

    public Offer addOffer(User owner, AddOfferForm offerForm, Category category) {
        Offer createdOffer = new Offer();
        createdOffer.setTitle(offerForm.getTitle());
        createdOffer.setDescription(offerForm.getDescription());
        createdOffer.setTimeOfIssue(LocalDateTime.now());
        createdOffer.setTimeOfExpiration(LocalDateTime.now().plusDays(30));
        // We checked form already in isCorrectForm no need to catch
        Integer price = Integer.parseInt(offerForm.getPrice());
        createdOffer.setCost(price);
        createdOffer.setActive(true);
        createdOffer.setOwner(owner);
        createdOffer.setCategory(category);
        return offerRepository.save(createdOffer);
    }

    public boolean isCorrectForm(AddOfferForm form, String outMessage) {
        if (form.getTitle().length() < 3) {
            outMessage = "Tytuł jest za krótki";
            return false;
        }
        if (form.getDescription().length() < 10) {
            outMessage = "Opis jest z krótki";
            return false;
        }
        Integer price;
        try {
            price = Integer.parseInt(form.getPrice());
        } catch (NumberFormatException e) {
            price = null;
        }
        if (price == null) {
            outMessage = "Cena wynajmu jest nieprawidłowa";
            return false;
        }
        return true;
    }

    public Offer saveOffer(Offer offer){
        return offerRepository.save(offer);
    }

}
