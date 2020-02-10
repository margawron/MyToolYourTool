package pl.polsl.inzoprog.myToolYourTool.models.orm;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Entity
@Getter
@Setter
@Table(name = "categories")

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "category_is_parent")
    private boolean isThisParent;

    @Column(name = "category_name")
    private String categoryName;

    @ManyToOne
    private Category parentOfCategory;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Category> children;
}
