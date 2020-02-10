package pl.polsl.inzoprog.myToolYourTool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Image;


@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {


}