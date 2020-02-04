package pl.polsl.inzoprog.myToolYourTool.models.orm;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Entity
@Getter
@Setter
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "message_content")
    private String content;

    /**
     * Mappings to other tables
     */
    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;


}
