package pl.polsl.inzoprog.myToolYourTool.models.forms;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
public class AddOfferForm {
    String title;
    String description;
    String price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
