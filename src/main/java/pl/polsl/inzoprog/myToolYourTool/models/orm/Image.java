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
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne
    private Offer originOffer;

    @Lob
    @Column(name = "image_blob", nullable = false, columnDefinition = "mediumblob")
    private byte[] imageBytes;
}
