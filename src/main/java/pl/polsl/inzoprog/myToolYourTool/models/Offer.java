package pl.polsl.inzoprog.myToolYourTool.models;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.description.modifier.Ownership;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Column(name = "offer_id",insertable = false, updatable = false)
    private Long id;

    @Column(name = "offer_title")
    private String title;

    @Column(name = "offer_desc")
    private String description;

    @Column(name = "offer_issue_date")
    private LocalDateTime timeOfIssue;

    @Column(name = "offer_expiry_date")
    private LocalDateTime timeOfExpiration;

    /**
     * Mappings to other tables
     */
    @ManyToOne
    private User owner;



}
