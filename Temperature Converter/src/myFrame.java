import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class myFrame extends JFrame implements ActionListener, ChangeListener {
    Color[] back= {new Color(0x000000),new Color(0x1F1F1F),new Color(0xffffff)};
    ImageIcon icon=new ImageIcon("thermometer.png");
    Color[] coldness={new Color(0xF81A1A),new Color(0x1BF514),new Color(0x5CC8D7)};
    Color currentColor= coldness[2];
    JLabel[] labels=new JLabel[3];
    JTextField[] textField=new JTextField[3];
    JPanel panel=new JPanel();
    JSlider slider=new JSlider(JSlider.VERTICAL,0,1000,273);
    Font font=new Font("Comic Sans", Font.BOLD, 20);
    Font fontText=new Font("Ariel", Font.BOLD, 30);
    boolean b=true;

    myFrame(){
        this.setSize(500,800);
        this.setTitle("Temperature Converter");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel.setLayout(null);
        panel.setBackground(back[0]);
        this.add(panel);
        setupText();
        setColor();
        slider.setBounds(350,40,40,600);
        slider.setBackground(back[1]);
        slider.setValue(273);
        slider.addChangeListener(this);
        panel.add(slider);
        this.setVisible(true);
    }
    public void setColor(){
        int t=slider.getValue();
        if(t<277)currentColor=coldness[2];
        else if(t<323) currentColor=coldness[1];
        else currentColor=coldness[0];
        for(int i=0;i<3;i++){
            textField[i].setForeground(currentColor);
            textField[i].setBorder(new LineBorder(currentColor,1));
        }
    }

    public void setupText(){
        for(int i=0;i<3;i++){
            labels[i]=new JLabel();
            textField[i]=new JTextField();
            textField[i].setBackground(back[1]);
            textField[i].setHorizontalAlignment(SwingConstants.RIGHT);
            textField[i].addActionListener(this);
            labels[i].setFont(font);
            textField[i].setFont(fontText);
            labels[i].setForeground(back[2]);
            panel.add(labels[i]);
            panel.add(textField[i]);
        }
        labels[0].setBounds(70,50,200,50);
        labels[0].setText("Fahrenheit");
        textField[0].setBounds(60,100,200,50);
        textField[0].setText("32");

        labels[1].setBounds(70,200,200,50);
        labels[1].setText("Celsius");
        textField[1].setBounds(60,250,200,50);
        textField[1].setText("0");

        labels[2].setBounds(70,350,200,50);
        labels[2].setText("Kelvin");
        textField[2].setBounds(60,400,200,50);
        textField[2].setText("273");
    }

    void changeTemp(float t,int i){
        if(i==0){
            float c=(t-32)*5.0f/9.0f;
            float k=c+273.15f;
            textField[1].setText(String.valueOf(c));
            textField[2].setText(String.valueOf(k));
        }
        else if(i==1){
            float k=t+273.15f;
            float f=(t*9)/5.0f+32f;
            textField[0].setText(String.valueOf(f));
            textField[2].setText(String.valueOf(k));
        }
        else if(i==2){
            float c=t-273.15f;
            float f=(c*9)/5.0f+32f;
            textField[0].setText(String.valueOf(f));
            textField[1].setText(String.valueOf(c));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0;i<3;i++)
            if(e.getSource()==textField[i]){
                try{
                    b=false;
                    float t=Float.parseFloat(textField[i].getText());
                    changeTemp(t,i);
                    slider.setValue((int)Float.parseFloat(textField[2].getText()));
                    setColor();
                }
                catch(Exception exception){
                    System.out.println("wrong temperature");
                }
                b=true;
            }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
            if(e.getSource()==slider && b){
                int t=slider.getValue();
                textField[2].setText(String.valueOf(t));
                changeTemp(t,2);
                setColor();
            }
    }
}
