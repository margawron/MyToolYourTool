package pl.polsl.inzoprog.myToolYourTool.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Offer;

import java.util.List;

@Repository
public interface OfferRepository extends CrudRepository<Offer, Long> {

    List<Offer> getOfferByOwnerId(Long id);

    List<Offer> findTop20ByIsActiveIsTrue();

}
