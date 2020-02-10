package pl.polsl.inzoprog.myToolYourTool.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query("select c from Category c where c.isThisParent = true")
    List<Category> findParents();

    @Query("select c from Category c where c.parentOfCategory.id = ?1")
    List<Category> findChildren(Long parent);
}
