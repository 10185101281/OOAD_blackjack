import java.util.*;

public class Dealer {
    private Integer chip;
    public HandCard handCard;
    Deck deck;
    public Dealer(){
        deck = new Deck();
        handCard = new HandCard();
    }
    public Poker deal(){
        Poker poker = deck.pokers.get(0);
        deck.pokers.remove(0);
        return poker;
    }
}
