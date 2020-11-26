import com.sun.org.apache.xerces.internal.xs.StringList;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HandCard {
    private ArrayList<Poker> pokers;
    private boolean hasCount;
    private Integer last;

    public HandCard(){
        pokers = new ArrayList<>();
        hasCount = false;
        last = 0;
    }

    public String getPoker(Poker poker){
        pokers.add(poker);
        hasCount = false;
        return poker.getSuit()+"_"+poker.getPoint();
    }

    public Integer count(){
        if(hasCount) return last;
        Integer numberA = 0, sum = 0;
        for (Poker poker : pokers){
            String point = poker.getPoint();
            if(point.equals("A")) {
                numberA++;
                sum += 1;
            }else if(point.equals("10") || point.equals("J") || point.equals("Q") || point.equals("K")) {
                sum += 10;
            }else {
                sum += (point.charAt(0)-'0');
            }
        }
        while(numberA>0 && sum+10<=21) {
            numberA--; sum+=10;
        }

        hasCount = true;
        last = sum;
        return sum;
    }

    public String display() {
        String s = "";
        boolean first = true;
        for (Poker poker : pokers) s += "("+poker.getPoint()+") ";
        return s;
    }
    public String display(boolean isDealer, boolean isEnd) {
        String s = "";
        boolean first = true;
        for (Poker poker : pokers) {
            if (first && isDealer && !isEnd) {
                s += "(***) ";
                first = false;
                continue;
            }
            s += "("+poker.getSuit()+" "+poker.getPoint()+") ";
        }
        return s;
    }
    public ArrayList<String> getPokers(boolean isDealer, boolean isEnd){
        ArrayList<String> list = new ArrayList<>();
        boolean first = true;
        for(Poker poker: pokers){
            if (first && isDealer && !isEnd) {
                list.add("back");
                first = false;
                continue;
            }
            list.add(poker.getSuit()+"_"+poker.getPoint());
        }
        return list;
    }
    public boolean isBlackJack(){
        if(count()!=21 || pokers.size() != 2) return false;
        for (Poker poker : pokers){
            if(poker.getPoint().equals("A")) return true;
        }
        return false;
    }

    public void clear(){
        pokers.clear();
    }
}
