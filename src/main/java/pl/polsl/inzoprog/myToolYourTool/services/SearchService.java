package pl.polsl.inzoprog.myToolYourTool.services;

import org.springframework.stereotype.Service;
import pl.polsl.inzoprog.myToolYourTool.models.forms.SearchForm;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Image;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Offer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;


@Service
public class SearchService {

    private EntityManagerFactory entityManagerFactory;
    private OfferService offerService;

    public SearchService(EntityManagerFactory entityManagerFactory, OfferService offerService) {
        this.entityManagerFactory = entityManagerFactory;
        this.offerService = offerService;
    }

    public List<Offer> getResults(SearchForm searchForm){
        EntityManager em = entityManagerFactory.createEntityManager();
        String advertisement = searchForm.getSearch();
        List<Offer> offerList =  em.createQuery("select o from Offer o where o.description like :custName", Offer.class)
                .setParameter("custName","%"+advertisement+"%")
                .setMaxResults(10)
                .getResultList();
        // Add images
        for(Offer offer: offerList){
            List<Image> images = offerService.getSingleImageOfOffer(offer.getId());
            offer.setOfferImages(images);
        }
        return offerList;
    }
}
