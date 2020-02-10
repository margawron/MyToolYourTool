package pl.polsl.inzoprog.myToolYourTool.models.orm;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "trasaction_start_date")
    private LocalDateTime startOfLending;

    @Column(name = "trasaction_end_date")
    private LocalDateTime endOfLending;

    /**
     * Mappings to other tables
     */
    @ManyToOne
    private User lender;

    @ManyToOne
    private Offer toolLended;

    @OneToOne
    private Opinion lenderOpinion;

    @OneToOne
    private Opinion ownerOpinion;

}
