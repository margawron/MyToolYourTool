package pl.polsl.inzoprog.myToolYourTool.services;

import org.springframework.stereotype.Service;
import pl.polsl.inzoprog.myToolYourTool.models.forms.SearchForm;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Offer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;


@Service
public class SearchService {

    private EntityManagerFactory entityManagerFactory;

    public SearchService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Offer> getResults(SearchForm searchForm){
        EntityManager em = entityManagerFactory.createEntityManager();
        String advertisement = searchForm.getSearch();
        return em.createQuery("select o from Offer o where o.description like :custName", Offer.class)
                .setParameter("custName","%"+advertisement+"%")
                .setMaxResults(10)
                .getResultList();
    }
}
