
public class Poker {
    final private Integer id;
    public Poker(Integer id){
        this.id = id;
    }
    public String getPoint(){
        Integer t = (id/4+1);
        if(t == 1) return "A";
        else if(t == 11) return "J";
        else if(t == 12) return "Q";
        else if(t == 13) return "K";
        else return t.toString();
    }
    public String getSuit(){
        String s;
        switch (id%4){
            case 0: s = "Spade"; break;
            case 1: s = "Heart"; break;
            case 2: s = "Diamond"; break;
            default: s = "Club"; break;
        }
        return s;
    }
}
