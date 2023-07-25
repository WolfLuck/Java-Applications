import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MyFrame extends JFrame implements ActionListener {
    JPanel panel;
    JButton[] buttons=new JButton[10];
    JButton[] operations={new JButton("+"), new JButton("-"),new JButton("*"),new JButton("/"),new JButton("=")};
    JButton dot=new JButton(".");
    JButton neg=new JButton("(-)");
    JButton del=new JButton("DEL");
    JButton AC=new JButton("AC");
    ImageIcon calc=new ImageIcon("calc.png");
    JTextField screen;
    Font font;
    Double num1,num2,result;
    char ch;

    MyFrame() {
        this.setSize(400, 650);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(0x000000));
        this.setResizable(false);
        this.setIconImage(calc.getImage());
        this.setLayout(null);
        this.setTitle("Calculator");

        font=new Font("Comic Sans", Font.BOLD, 30);
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 3,20,20));
        panel.setBounds(20, 140, 250, 350);
        panel.setBackground(new Color(0x000000));
        //panel.setFocusable(false);

        screen = new JTextField();
        screen.setBounds(20, 40, 345, 60);
        screen.setText("");
        screen.setFocusable(false);
        screen.setEditable(false);
        //screen.addActionListener(this);
        screen.setForeground(new Color(0x22BB22));
        screen.setBackground(new Color(0x212121));
        screen.setBorder(null);
        screen.setFont(font);

        this.add(screen);
        this.add(panel);
        b();

        this.setVisible(true);
    }

    public void b()
    {
        for(int i=9;i>=0;i--) {
            buttons[i]=new JButton(String.valueOf(i));
            panel.add(buttons[i]);
            buttonSetup(buttons[i]);
        }
        panel.add(dot);
        buttonSetup(dot);
        panel.add(neg);
        buttonSetup(neg);

        del.setBounds(20,510,110,70);
        AC.setBounds(160,510,110,70);
        buttonSetup(del);
        buttonSetup(AC);
        this.add(del);
        this.add(AC);

       for(int i=0;i<5;i++) {
           this.add(operations[i]);
           operations[i].setBounds(290,141+i*92,70,70);
           buttonSetup(operations[i]);
       }
    }
    void buttonSetup(JButton j)
    {
        j.addActionListener(this);
        j.setFont(font);
        j.setBackground(new Color(0x212121));
        j.setForeground(new Color(0x00CC00));
        j.setBorder(null);
        j.setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int j=0;j<10;j++)
          if(e.getSource()==buttons[j])
                screen.setText(screen.getText().concat(String.valueOf(j)));

        if(e.getSource()==AC) {
            screen.setText("");
        }

        if(e.getSource()==del) {
         String txt=screen.getText();
         int a=txt.length();
         screen.setText(txt.substring(0,a-1));
        }

        if(e.getSource()==dot) {
            if (screen.getText().indexOf('.') == -1)
                screen.setText(screen.getText().concat("."));
        }
        if(e.getSource()==neg) {
                screen.setText(screen.getText().concat("-"));
        }

        if(e.getSource()==operations[0]){ch='+';num1=Double.parseDouble(screen.getText());screen.setText("");}
        if(e.getSource()==operations[1]){ch='-';num1=Double.parseDouble(screen.getText());screen.setText("");}
        if(e.getSource()==operations[2]){ch='*';num1=Double.parseDouble(screen.getText());screen.setText("");}
        if(e.getSource()==operations[3]){ch='/';num1=Double.parseDouble(screen.getText());screen.setText("");}
        if(e.getSource()==operations[4]){
            num2=Double.parseDouble(screen.getText());
            switch(ch){ case '+':result=num1+num2;break;
                        case '-':result=num1-num2;break;
                        case '*':result=num1*num2;break;
                        case '/':result=num1/num2;break;
                        default:break;
            }
            screen.setText(String.valueOf(result));
            num1=result;
        }

    }

}
