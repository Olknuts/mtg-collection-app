package se.dala.mtg_collection_app.model;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Expansion {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cards")
    @Expose
    private List<Card> cards = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

}