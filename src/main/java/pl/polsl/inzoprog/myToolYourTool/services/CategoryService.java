package pl.polsl.inzoprog.myToolYourTool.services;

import org.springframework.stereotype.Service;
import pl.polsl.inzoprog.myToolYourTool.models.orm.Category;
import pl.polsl.inzoprog.myToolYourTool.repositories.CategoryRepository;

import java.util.List;


/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getParentCategories(){
        List<Category> categories = categoryRepository.findParents();
        for(Category category: categories){
            List<Category> childrens = categoryRepository.findChildren(category.getId());
            category.setChildren(childrens);
        }
            return categories;
    }


    private void init(){

        // parent
        Category elektryczne = new Category();
        elektryczne.setCategoryName("Elektryczne");
        elektryczne.setThisParent(true);

        categoryRepository.save(elektryczne);
        // childrens
        Category spawarki = new Category();
        spawarki.setCategoryName("Spawarki");
        spawarki.setThisParent(false);
        spawarki.setParentOfCategory(elektryczne);

        categoryRepository.save(spawarki);

        Category wiertarki = new Category();
        wiertarki.setCategoryName("Wiertarki");
        wiertarki.setThisParent(false);
        wiertarki.setParentOfCategory(elektryczne);

        categoryRepository.save(wiertarki);

        Category szlifierki = new Category();
        szlifierki.setCategoryName("Spawarki");
        szlifierki.setThisParent(false);
        szlifierki.setParentOfCategory(elektryczne);

        categoryRepository.save(szlifierki);

        Category cleaners = new Category();
        cleaners.setCategoryName("Urządzenia czyszczące");
        cleaners.setThisParent(false);
        cleaners.setParentOfCategory(elektryczne);

        categoryRepository.save(cleaners);

        // parent
        Category mechaniczne = new Category();
        mechaniczne.setCategoryName("Mechaniczne");
        mechaniczne.setThisParent(true);

        categoryRepository.save(mechaniczne);
        //childrens
        Category klucze = new Category();
        klucze.setCategoryName("Klucze");
        klucze.setThisParent(false);
        klucze.setParentOfCategory(mechaniczne);

        categoryRepository.save(klucze);

        Category wkrtaki = new Category();
        wkrtaki.setCategoryName("Wkrętaki");
        wkrtaki.setThisParent(false);
        wkrtaki.setParentOfCategory(mechaniczne);

        categoryRepository.save(wkrtaki);

        Category sciski = new Category();
        sciski.setCategoryName("Ściski");
        sciski.setThisParent(false);
        sciski.setParentOfCategory(mechaniczne);

        categoryRepository.save(sciski);

        Category szczypce = new Category();
        szczypce.setCategoryName("Szczypce, nożyce, obcęgi");
        szczypce.setThisParent(false);
        szczypce.setParentOfCategory(mechaniczne);

        categoryRepository.save(szczypce);

        // parent
        Category spalinowe = new Category();
        spalinowe.setCategoryName("Spalinowe");
        spalinowe.setThisParent(true);

        categoryRepository.save(spalinowe);

        // childrens
        Category kosiarki = new Category();
        kosiarki.setCategoryName("Kosiarki");
        kosiarki.setThisParent(false);
        kosiarki.setParentOfCategory(spalinowe);

        categoryRepository.save(kosiarki);

        Category pily = new Category();
        pily.setCategoryName("Piły");
        pily.setThisParent(false);
        pily.setParentOfCategory(spalinowe);

        categoryRepository.save(pily);

        Category zgeszczarki = new Category();
        zgeszczarki.setCategoryName("Zagęszczarki");
        zgeszczarki.setThisParent(false);
        zgeszczarki.setParentOfCategory(spalinowe);

        categoryRepository.save(zgeszczarki);

        Category agregaty = new Category();
        agregaty.setCategoryName("Agregaty prądotwórcze");
        agregaty.setThisParent(false);
        agregaty.setParentOfCategory(spalinowe);

        categoryRepository.save(agregaty);

    }
}

