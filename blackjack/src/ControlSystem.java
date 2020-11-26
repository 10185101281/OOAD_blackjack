import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class ControlSystem {

    public static Dealer dealer;
    public static Player player;
    public static Bet bet;
    private static void createUserPanel(){
        UserPanel userPanel = new UserPanel("Black Jack");
        userPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userPanel.setSize(1200, 800);
        userPanel.setVisible(true);
    }
    public static boolean setChip(Integer chip){
        if(player.getChip() < chip) return false;
        player.subChip(chip);
        bet = new Bet(chip);
        return true;
    }
    public static boolean addChip(Integer chip){
        if(player.getChip() < chip) return false;
        player.subChip(chip);
        bet.addChip(chip);
        return true;
    }
    public static void init(){
        dealer = new Dealer();
        player.handCard.clear();
        for(int i=0; i<2; i++) dealer.handCard.getPoker(dealer.deal());
        for(int i=0; i<2; i++) player.handCard.getPoker(dealer.deal());
    }
    public static String playerGetPoker(){
        return player.handCard.getPoker(dealer.deal());
    }
    public static String dealerGetPoker(){
        return dealer.handCard.getPoker(dealer.deal());
    }
    public static boolean checkOverFlow(HandCard handCard){
        return handCard.count() > 21;
    }
    public static Integer judgeWinner(){
        if(player.handCard.count() > 21) {
            bet.setOdds(0.0);
            return -1;
        }
        if(dealer.handCard.count() > 21) {
            bet.setOdds(2.0);
            return 1;
        }
        Integer count1 = dealer.handCard.count();
        Integer count2 = player.handCard.count();
        if(count1 > count2) {
            bet.setOdds(0.0);
            return -1;
        }
        else if(count1 < count2) {
            bet.setOdds(2.0);
            return 1;
        }
        else {
            if(dealer.handCard.isBlackJack() && !player.handCard.isBlackJack()) {
                bet.setOdds(0.0);
                return -1;
            }
            else if(!dealer.handCard.isBlackJack() && player.handCard.isBlackJack()) {
                bet.setOdds(3.0);
                return 2;
            } else {
                bet.setOdds(1.0);
                return 0;
            }
        }
    }
    public static void settle(){
        player.addChip(bet.calculate());
    }
    public static Integer getChip(){
        return bet.getChip();
    }
    public static void main(String[] args){
        player = new Player();
        createUserPanel();

        //if(player.getChip() < 100){ ("Go fuck yourself, pauper!");
        /*
            while(player.handCard.count() < 11) {
                System.out.println("Total points less than 11 points, must hit.");
                player.handCard.getPoker(dealer.deal());

                dealer.handCard.display(true, false);
                player.handCard.display(false, false);
            }
         */
    }
}
