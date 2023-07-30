import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GameSession implements ActionListener {
    int score;
    private int totalQuestions;
    int[] order;
    private int currentQuestions=10; //set for questions for a particular round
    private int i;
    Timer t;
    boolean over;
    JSlider sl;
    GameSession(int n){
        System.out.println("session created");
        score=0;
        totalQuestions= n;
        order=new int[n];
        i=0;
        over=false;
        setQuestions();
        for(int p: order)System.out.print(p+" ");
    }

    void setQuestions(){
        //randomize question order
        ArrayList<Integer> numbers=new ArrayList<>();
        for(int p=0;p<totalQuestions;p++)numbers.add(p);
        Random rndm = new Random();
        for (int a = 0; a < currentQuestions; a++) {
            int rndmIndx = rndm.nextInt(numbers.size());
            order[a] = numbers.get(rndmIndx);
            numbers.remove(rndmIndx);
        }
    }
    void setScore(JLabel label){
        score+= (sl.getMaximum()-sl.getValue())*100;
        label.setText("Score: "+String.valueOf(score));
    }
    void GameOver(JButton play,JTextArea area,JButton[] op){
        if(sl!=null)sl.setVisible(false);
        area.setVisible(false);
        for(int a=0;a<4;a++)op[a].setVisible(false);
        play.setVisible(true);
        over=true;
        t.stop();
    }
    int getCurrentQuestions(){
        return currentQuestions;
    }

    int nextQuestion(JSlider slider){
        if(i==currentQuestions) return 0;
        t=new Timer(1000,this);
        sl=slider;
        sl.setValue(0);
        sl.setVisible(true);
        t.start();
        return order[i++];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==t){
            int i=sl.getValue();
            sl.setValue(i+1);
        }
    }
}
