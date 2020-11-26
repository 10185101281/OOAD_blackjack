import java.util.ArrayList;

public class Player {
    private Integer chip;
    private final Integer id;
    private static Integer number = 0;
    public HandCard handCard;

    public Player(){
        handCard = new HandCard();
        this.chip =  1000;
        this.id = ++number;
    }
    public Player(Integer chip){
        handCard = new HandCard();
        this.chip = chip;
        this.id = ++number;
    }

    public void subChip(Integer d){
        chip = chip - d;
    }
    public void addChip(Integer d){
        chip = chip + d;
    }
    public Integer getChip(){
        return chip;
    }
}
