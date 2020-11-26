import javafx.scene.control.RadioButton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class UserPanel extends JFrame{
    private Container contentPane;
    private JPanel playerPanel, showPanel;
    private JPanel enterPage, mainPage;

    private JLabel avatar, chipText;
    private void initPlayerPanel(){
        playerPanel = new JPanel(new FlowLayout());
        playerPanel.setPreferredSize(new Dimension(1200, 100));
        playerPanel.setBackground(new Color(0xF5F5F5));
        playerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));


        Integer randomInteger = new Random().nextInt(16);
        ImageIcon imageIcon = new ImageIcon("src/picture/avatar/"+randomInteger+".png");
        System.out.println("src/picture/avatar/"+randomInteger+".png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
        avatar = new JLabel(imageIcon);
        avatar.setBorder(BorderFactory.createLineBorder(new Color(0x696969),4,true));

        chipText = new JLabel();
        chipText.setPreferredSize(new Dimension(1000, 90));
        chipText.setFont(new Font("Dialog",Font.BOLD,25));
        chipText.setHorizontalAlignment(SwingConstants.CENTER);
        chipText.setVerticalAlignment(SwingConstants.CENTER);
        chipText.setOpaque(true);
        chipText.setBackground(new Color(0xF0F8FF));
        chipText.setBorder(BorderFactory.createLineBorder(Color.BLACK,1,true));
        refreshChipCount(false);

        playerPanel.add(avatar);
        playerPanel.add(chipText);
    }

    private void initShowPanel(){
        showPanel = new JPanel();
        showPanel.setPreferredSize(new Dimension(1200, 700));

        initMainPage();
        initEnterPage();

        showPanel.setLayout(new CardLayout());
        showPanel.add(enterPage, "0");
        showPanel.add(mainPage, "1");
    }

    private void refreshChipCount(boolean isEnd){
        Integer chip = ControlSystem.player.getChip();
        chipText.setText("Your remaning chips: "+chip);
        if(isEnd && chip < 100){
            JOptionPane.showMessageDialog(this,"You're out of game...(with remaining chip less of 100)","",JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    private JPanel enterPageContent, enterPageButtons;
    private JLabel enterPageText;
    private JTextField enterPageIn;
    private JButton enterPageConfirmButton, enterPageAllInButton;
    private boolean valid(String s){
        if(s.length() == 0) return false;
        if(s.charAt(0) == '0') return false;
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i)>='0' && s.charAt(i)<='9') continue;
            return false;
        }
        return true;
    }
    private void initEnterPage(){
        enterPage = new JPanel(new BorderLayout());
        enterPage.setPreferredSize(new Dimension(1200, 700));
        enterPageContent = new JPanel(new FlowLayout(FlowLayout.CENTER));
        enterPageContent.setPreferredSize(new Dimension(1200, 585));
        enterPageContent.setBorder(BorderFactory.createLineBorder(new Color(0x8B4513),2));
        enterPageContent.setBackground(new Color(0x6E8B3D));
        enterPageButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        enterPageButtons.setPreferredSize(new Dimension(1200, 100));
        enterPageButtons.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        enterPageButtons.setBackground(new Color(0xF5F5F5));
        enterPage.add(enterPageContent, BorderLayout.CENTER);
        enterPage.add(enterPageButtons, BorderLayout.PAGE_END);

        ImageIcon imageIcon = new ImageIcon("src/picture/chip/1.jpg");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(90,90,Image.SCALE_DEFAULT));
        JLabel picture = new JLabel(imageIcon);
        picture.setBorder(BorderFactory.createLineBorder(new Color(0x6E8B3D),20,true));
        enterPageContent.add(picture);

        enterPageText = new JLabel();
        enterPageText.setPreferredSize(new Dimension(300, 50));
        enterPageText.setHorizontalAlignment(SwingConstants.CENTER);
        enterPageText.setVerticalAlignment(SwingConstants.CENTER);
        enterPageText.setText("Enter your betting chips: ");
        enterPageText.setFont(new Font("Dialog",Font.ITALIC,18));
        enterPageIn = new JTextField(20);
        enterPageIn.setPreferredSize(new Dimension(300, 50));
        enterPageIn.setHorizontalAlignment(SwingConstants.CENTER);
        refreshEnterPage();
        enterPageContent.add(enterPageText);
        enterPageContent.add(enterPageIn);

        ImageIcon imageIcon2 = new ImageIcon("src/picture/other/0.png");
        imageIcon2.setImage(imageIcon2.getImage().getScaledInstance(280,280,Image.SCALE_DEFAULT));
        JLabel picture2 = new JLabel(imageIcon2);
        picture2.setPreferredSize(new Dimension(1000,280));
        enterPageContent.add(picture2);

        enterPageConfirmButton = new JButton("Confirm");
        enterPageConfirmButton.setPreferredSize(new Dimension(200, 80));
        enterPageConfirmButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String s = enterPageIn.getText();
                        if(!valid(s)){
                            JOptionPane.showMessageDialog(enterPageContent,"Please enter a legal number!","",JOptionPane.WARNING_MESSAGE);
                        } else {
                            Integer chip = 0;
                            for(int i=0; i<s.length(); i++){
                                chip = chip*10 + (s.charAt(i)-'0');
                            }
                            if(ControlSystem.setChip(chip)){
                                refreshChipCount(false);

                                CardLayout cardLayout = (CardLayout)showPanel.getLayout();
                                ControlSystem.init();
                                refreshMainPage(false);
                                mainPageHitButton.setEnabled(true);
                                mainPageStopButton.setEnabled(true);
                                mainPageDoubleButton.setEnabled(true);
                                mainPageNextTurnButton.setEnabled(false);
                                cardLayout.show(showPanel, "1");
                            } else {
                                JOptionPane.showMessageDialog(enterPageContent,"The amount of the bet cannot exceed your balance!","",JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                }
        );
        enterPageButtons.add(enterPageConfirmButton);

        enterPageAllInButton = new JButton("ALL IN!!");
        enterPageAllInButton.setPreferredSize(new Dimension(200, 80));
        enterPageAllInButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Integer chip = ControlSystem.player.getChip();
                        ControlSystem.setChip(chip);
                        refreshChipCount(false);

                        CardLayout cardLayout = (CardLayout)showPanel.getLayout();
                        ControlSystem.init();
                        refreshMainPage(false);
                        mainPageHitButton.setEnabled(true);
                        mainPageStopButton.setEnabled(true);
                        mainPageDoubleButton.setEnabled(true);
                        mainPageNextTurnButton.setEnabled(false);
                        cardLayout.show(showPanel, "1");
                    }
                }
        );
        enterPageButtons.add(enterPageAllInButton);
    }
    private void refreshEnterPage(){
        enterPageIn.setText("");
    }

    private JPanel mainPageContent, mainPageButtons;
    private JPanel dealerDesk, playerDesk;
    private JButton mainPageHitButton, mainPageStopButton, mainPageDoubleButton, mainPageNextTurnButton;
    private void initMainPage(){
        mainPage = new JPanel(new BorderLayout());
        mainPage.setPreferredSize(new Dimension(1200, 700));
        mainPageContent = new JPanel(new FlowLayout());
        mainPageContent.setPreferredSize(new Dimension(1200, 600));
        mainPageButtons= new JPanel(new FlowLayout());
        mainPageButtons.setPreferredSize(new Dimension(1200, 100));
        mainPageButtons.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        mainPageButtons.setBackground(new Color(0xF5F5F5));
        mainPage.add(mainPageContent,BorderLayout.CENTER);
        mainPage.add(mainPageButtons,BorderLayout.PAGE_END);

        dealerDesk = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playerDesk = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dealerDesk.setPreferredSize(new Dimension(1200, 280));
        playerDesk.setPreferredSize(new Dimension(1200, 280));
        Border outside = BorderFactory.createLineBorder(new Color(0x8B4513),20, true);
        Border inside = BorderFactory.createLineBorder(new Color(0xE0FFFF),5);
        Border dealerInside = BorderFactory.createTitledBorder(inside,"Dealer");
        Border playerInside = BorderFactory.createTitledBorder(inside, "Player");
        dealerDesk.setBorder(BorderFactory.createCompoundBorder(outside,dealerInside));
        playerDesk.setBorder(BorderFactory.createCompoundBorder(outside,playerInside));
        dealerDesk.setBackground(new Color(0x6E8B3D));
        playerDesk.setBackground(new Color(0x6E8B3D));

        mainPageContent.add(dealerDesk);
        mainPageContent.add(playerDesk);

        mainPageHitButton = new JButton("Hit");
        mainPageStopButton = new JButton("Stop");
        mainPageDoubleButton  = new JButton("Double");
        mainPageNextTurnButton = new JButton("NextTurn");

        mainPageHitButton.setPreferredSize(new Dimension(200, 80));
        mainPageStopButton.setPreferredSize(new Dimension(200, 80));
        mainPageDoubleButton.setPreferredSize(new Dimension(200, 80));
        mainPageNextTurnButton.setPreferredSize(new Dimension(200, 80));

        mainPageHitButton.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ControlSystem.playerGetPoker();
                        refreshMainPage(false);
                        if(ControlSystem.checkOverFlow(ControlSystem.player.handCard)) {
                            mainPageStopButton.doClick();
                        }
                    }
                }
        );
        mainPageDoubleButton.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Integer chip = ControlSystem.getChip();
                        if(ControlSystem.addChip(chip)) {
                            refreshChipCount(false);
                        } else {
                            JOptionPane.showMessageDialog(mainPageContent,"No more chips...","",JOptionPane.INFORMATION_MESSAGE);
                        }
                        mainPageDoubleButton.setEnabled(false);
                    }
                }
        );
        mainPageStopButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!ControlSystem.checkOverFlow(ControlSystem.player.handCard)){
                            while(ControlSystem.dealer.handCard.count() < 17){
                                ControlSystem.dealerGetPoker();
                                refreshMainPage(false);
                                //try{Thread.sleep(500);}catch(Exception et){}
                            }
                        }
                        refreshMainPage(true);
                        Integer ans = ControlSystem.judgeWinner();
                        ControlSystem.settle();
                        refreshChipCount(false);
                        if (ans == 2) {
                            JOptionPane.showMessageDialog(mainPageContent,"You Win with a BLackJack!!!","",JOptionPane.PLAIN_MESSAGE);
                        } else if(ans == 1){
                            JOptionPane.showMessageDialog(mainPageContent,"You Win!","",JOptionPane.PLAIN_MESSAGE);
                        } else if(ans == -1){
                            JOptionPane.showMessageDialog(mainPageContent,"You Lose...","",JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(mainPageContent,"Draw...","",JOptionPane.PLAIN_MESSAGE);
                        }
                        refreshChipCount(true);
                        mainPageHitButton.setEnabled(false);
                        mainPageStopButton.setEnabled(false);
                        mainPageDoubleButton.setEnabled(false);
                        mainPageNextTurnButton.setEnabled(true);
                    }
                }
        );
        mainPageNextTurnButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        CardLayout cardLayout = (CardLayout)showPanel.getLayout();
                        ControlSystem.init();
                        refreshEnterPage();
                        cardLayout.show(showPanel, "0");
                    }
                }
        );
        mainPageButtons.add(mainPageHitButton);
        mainPageButtons.add(mainPageDoubleButton);
        mainPageButtons.add(mainPageStopButton);
        mainPageButtons.add(mainPageNextTurnButton);
    }
    private void refreshMainPage(boolean isEnd){
        dealerDesk.removeAll();
        dealerDesk.repaint();
        ArrayList<String> list = ControlSystem.dealer.handCard.getPokers(true,isEnd);
        for(String s: list){
            ImageIcon imageIcon = new ImageIcon("src/picture/poker/"+s+".JPG");
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(68,102,Image.SCALE_DEFAULT));
            JLabel t = new JLabel(imageIcon);

            dealerDesk.add(t);
        }
        dealerDesk.updateUI();

        playerDesk.removeAll();
        playerDesk.repaint();
        list = ControlSystem.player.handCard.getPokers(false,isEnd);
        for(String s: list){
            ImageIcon imageIcon = new ImageIcon("src/picture/poker/"+s+".jpg");
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(68,102,Image.SCALE_DEFAULT));
            JLabel t = new JLabel(imageIcon);

            playerDesk.add(t);
        }
        playerDesk.updateUI();
    }

    public UserPanel(String title){
        super(title);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setLocation(screenWidth/4, screenHeight/10);

        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(1200, 800));

        initPlayerPanel();
        initShowPanel();
        contentPane.add(playerPanel, BorderLayout.PAGE_START);
        contentPane.add(showPanel, BorderLayout.CENTER);

        //JOptionPane.showMessageDialog(this,"Game Start!","",JOptionPane.PLAIN_MESSAGE);
        CardLayout cardLayout = (CardLayout)showPanel.getLayout();
        refreshChipCount(false);
        refreshEnterPage();
        cardLayout.show(showPanel,"0");
    }
}
