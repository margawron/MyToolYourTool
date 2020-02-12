package pl.polsl.inzoprog.myToolYourTool.models.orm;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Entity
@Getter
@Setter
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "offer_id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "offer_title")
    private String title;

    @Column(name = "offer_desc")
    @Lob
    private String description;

    @Column(name = "offer_issue_date")
    private LocalDateTime timeOfIssue;

    @Column(name = "offer_expiry_date")
    private LocalDateTime timeOfExpiration;

    @Column(name = "offer_cost")
    private Integer cost;

    @Column(name = "offer_active")
    private boolean isActive;

    /**
     * Mappings to other tables
     */
    @ManyToOne
    private User owner;

    // Wszystkie wypożyczenia
    @OneToMany(mappedBy = "toolLended")
    private List<Transaction> transactions;

    // Kategoria przedmiotu
    @OneToOne
    private Category category;

    // Zdjęcia przedmiotu
    @OneToMany
    @JoinTable(name = "image_to_offer_map_table",
            joinColumns = @JoinColumn(name = "offer_origin_id", referencedColumnName = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "image_id")
    )
    private List<Image> offerImages;

}
