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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_passwordSalt")
    private String passwordSalt;

    @Column(name = "user_createdAt")
    private LocalDateTime createdAt;

    @Column(name = "user_mail")
    private String mail;

    @Column(name = "user_postal_code")
    private Integer postalCode;

    /**
     * Mappings to other tables
     */
    @OneToMany(mappedBy = "owner")
    private List<Opinion> issuedOpinions;

    @OneToMany(mappedBy = "target")
    private List<Opinion> receivedOpinions;

    @OneToMany(mappedBy = "receiver")
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "owner")
    private List<Offer> createdOffers;

    @JoinTable(name = "who_used_offers",
            joinColumns = @JoinColumn(name = "lender_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id", referencedColumnName = "offer_id")
    )
    @OneToMany
    private List<Offer> usedOffers;




}