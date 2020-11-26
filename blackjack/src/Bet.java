import org.omg.PortableInterceptor.INACTIVE;

public class Bet {
    private Double odds;
    private Integer chip;
    public Bet(Integer chip){
        this.chip = chip;
        this.odds = 2.0;
    }
    void setOdds(Double d){
        this.odds = d;
    }
    public Integer getChip(){
        return chip;
    }
    public void addChip(Integer chip){
        this.chip += chip;
    }
    public Integer calculate(){
        return (int)(chip*odds);
    }
}
