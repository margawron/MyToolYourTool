package pl.polsl.inzoprog.myToolYourTool.models;

import lombok.Getter;
import lombok.Setter;
import pl.polsl.inzoprog.myToolYourTool.models.enums.ISSUE_DIRECTION;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Marcel Gawron
 * @version 1.0
 */


@Entity
@Getter
@Setter
@Table(name = "opinions")
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "opinion_id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "opinion_content")
    private String content;

    @Column(name = "opinion_timeOfIssue")
    private LocalDateTime timeOfIssue;

    @Column(name = "opinion_issue_dir")
    private ISSUE_DIRECTION issueDirection;

    /**
     * Score from 1-10
     */
    @Column(name = "opinion_score")
    private Integer score;

    /**
     * Mappings to other tables
     */
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "target_id")
    private User target;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer originToolOffer;


}
