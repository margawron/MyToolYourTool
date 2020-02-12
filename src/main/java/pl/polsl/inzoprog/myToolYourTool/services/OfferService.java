package pl.polsl.inzoprog.myToolYourTool.services;

import org.springframework.stereotype.Service;
import pl.polsl.inzoprog.myToolYourTool.models.forms.AddOfferForm;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Category;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Offer;
import pl.polsl.inzoprog.myToolYourTool.models.orm.User;
import pl.polsl.inzoprog.myToolYourTool.repositories.OfferRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    public OfferService(OfferRepository offerRepository, EntityManagerFactory entityManagerFactory) {
        this.offerRepository = offerRepository;
        this.entityManagerFactory = entityManagerFactory;
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
        return typedQuery.getResultList();
    }

    public List<Offer> getUserOffers(Long id){
        return offerRepository.getOfferByOwnerId(id);
    }

    public List<Offer> getOfferFromCategory(Long categoryId){
        return offerRepository.findTop20ByCategoryId(categoryId);
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
}
