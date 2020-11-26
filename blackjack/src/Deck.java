import java.lang.reflect.Array;
import java.util.*;

public class Deck {
    ArrayList<Poker> pokers;
    public Deck(){
        pokers = new ArrayList<>();
        for(int i=0; i<52; i++) pokers.add(new Poker(i));
        Collections.shuffle(pokers);
    }
}
