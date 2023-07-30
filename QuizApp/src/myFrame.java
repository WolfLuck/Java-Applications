import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class myFrame extends JFrame implements ActionListener, ChangeListener {
    ArrayList<Question> questions=new ArrayList<>();
    JSlider slider=new JSlider(0,30);
    JButton play=new JButton("Play");
    JTextArea sawaal=new JTextArea();
    JButton[] options=new JButton[4];
    JPanel panel;
    JLabel HighScore;
    Font font=new Font("Ariel",Font.BOLD,30);
    GameSession session;
    int n=10;
    int nq=0;
    int currentIndex;
    myFrame(){
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("Game Master");
        setup();

        panel =new JPanel();
        panel.setBounds(0,0,1200,800);
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);

        play.setFont(font);
        play.setBounds(400, 300, 400, 200);
        play.setBackground(Color.BLACK);
        play.setForeground(Color.WHITE);
        play.setFocusable(false);
        play.addActionListener(this);
        panel.add(play);

        slider.setBounds(100,700,1000,20);
        slider.setBackground(Color.BLACK);
        slider.setEnabled(false);
        slider.addChangeListener(this);
        slider.setVisible(false);
        panel.add(slider);

        sawaal.setBounds(100,100,1000,100);
        sawaal.setEditable(false);
        sawaal.setFont(font);
        sawaal.setBackground(Color.DARK_GRAY);
        sawaal.setForeground(Color.WHITE);
        sawaal.setLineWrap(true);
        sawaal.setWrapStyleWord(true);
        sawaal.setVisible(false);
        panel.add(sawaal);

        HighScore=new JLabel("Score: 0");
        HighScore.setBounds(950,20,200,100);
        HighScore.setForeground(Color.WHITE);
        HighScore.setFont(font);
        panel.add(HighScore);

        for(int i=0;i<4;i++){
            options[i]=new JButton(String.valueOf(i));
            options[i].setVisible(false);
            options[i].setFocusable(false);
            options[i].setFont(font);
            options[i].addActionListener(this);
            options[i].setBackground(new Color(0x2B8FE8));
            panel.add(options[i]);
        }
        options[0].setBounds(150,300,400,100);
        options[1].setBounds(650,300,400,100);
        options[2].setBounds(150,500,400,100);
        options[3].setBounds(650,500,400,100);


        this.add(panel);
        this.setVisible(true);
    }
    void setup(){
        try {
            File file = new File("QuestionList.csv");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;
            while((line = br.readLine()) != null) {
                tempArr = line.split(",");
                int i=0;
                while(i<tempArr.length) {
                    String q = tempArr[i];
                    String op1 = tempArr[i+1];
                    String op2 = tempArr[i+2];
                    String op3 = tempArr[i+3];
                    String op4 = tempArr[i+4];
                    int l = Integer.parseInt(tempArr[i+5]);
                    Question ques = new Question(q, op1, op2, op3, op4, l);
                    questions.add(ques);
                    i=i+6;
                }
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
            }
    }

    void createSession(int n){
        if(session==null || session.over){
            session= new GameSession(n);
            nq=0;
            HighScore.setText("Score: 0");
            SetText();
        }
    }

    void SetText(){
        if(nq==session.getCurrentQuestions()){
            session.GameOver(play,sawaal,options);
            return;
        }
        nq++;
        sawaal.setVisible(true);
        currentIndex= session.nextQuestion(slider);
        sawaal.setText(questions.get(currentIndex).getQues());
        for(int a=0;a<4;a++){
            options[a].setVisible(true);
            options[a].setText(questions.get(currentIndex).getOption(a+1));
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==play){
            createSession(n);
            play.setVisible(false);
            play.setText("Play Again");
        }
        for(int i=0;i<4;i++){
            if(e.getSource()==options[i]){
                if(questions.get(currentIndex).getCorrect()==i+1){
                    session.setScore(HighScore);
                    SetText();
                }
                else{
                    session.GameOver(play,sawaal,options);
                }
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource()==slider){
            if(slider.getValue()>=slider.getMaximum())
                session.GameOver(play,sawaal,options);
        }
    }
}
