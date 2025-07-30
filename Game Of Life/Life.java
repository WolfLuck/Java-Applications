import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

public class Life extends JFrame implements MouseListener, ActionListener {
    final int screenWidth=800,screenHeight=800;
    int units=20;
    int delay=75;
    int x,y;
    int[][] stat=new int[screenWidth/units][screenHeight/units];
    int[][] update=new int[screenWidth/units][screenHeight/units];

    boolean k=false,l=true;
    JToggleButton button=new JToggleButton("Start");
    Life(){
        this.setLayout(null);
        this.setTitle("Game of Life");
        this.setSize(screenWidth+100,screenHeight);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        UIManager.put(this.rootPane, new ColorUIResource(Color.black ));
        UIManager.put(this.rootPane, new ColorUIResource(Color.WHITE));
        UIManager.put("InternalFrame.titleFont", new Font("Dialog", Font.PLAIN, 11));
        button.setBounds(screenWidth+10,0,90,200);
        button.setFocusable(false);
        button.addActionListener(this);
        button.setSelected(false);
        this.add(button);

        this.addMouseListener(this);

        for(int i=0;i<screenWidth;i=i+units) 
            for(int j=0;j<screenHeight;j=j+units) {
                stat[i/units][j/units]=0;
                update[i/units][j/units]=0;
            }
    }
    public void paint(Graphics g){
        Graphics2D g2d=(Graphics2D) g;

        if(!k)super.paint(g);
        for(int i=0;i<screenWidth+units;i=i+units){
            g2d.drawLine(i,0,i,screenHeight);
        }
        for(int j=0;j<screenHeight;j=j+units){
            g2d.drawLine(0,j,screenWidth,j);
        }
        if(k&&l)
         draw(g,x,y);
        else if(!l)
            disp(g);

    }
    public void draw(Graphics g,int a,int b){
        Graphics2D g2d=(Graphics2D) g;
        g2d.setColor(Color.BLUE);
        if(x<screenWidth&&y<screenHeight){
        g2d.fillRect(a,b,units,units);
        stat[a/units][b/units]=1;
    }}

    public void disp(Graphics g){
            for (int i = 0; i < screenWidth; i = i + units) {
                for (int j = 0; j < screenHeight; j = j + units) {
                    int p = 0;
                    int a = i / units, b = j / units;
                    if (a - 1 >= 0 && stat[a - 1][b] == 1) p++;
                    if (a + 1 < screenWidth/units && stat[a+1][b] == 1) p++;

                    if (a - 1 >= 0 && b - 1 >= 0 && stat[a - 1][b - 1]==1) p++;
                    if(b - 1 >= 0 && stat[a][b - 1]==1) p++;
                    if (a + 1 < screenWidth/units && b - 1 >= 0 && stat[a + 1][b - 1]==1) p++;

                    if (a - 1 >= 0 && b + 1 < screenHeight/units && stat[a - 1][b + 1]==1) p++;
                    if (b + 1 < screenHeight/units && stat[a][b + 1]==1) p++;
                    if (a + 1 < screenWidth/units && b + 1 < screenHeight/units && stat[a + 1][b + 1]==1) p++;

                    if(p<2)update[a][b]=0;
                    else if(stat[a][b]==1 && p<=3)update[a][b]=1;
                    else if(stat[a][b]==0 && p==3)update[a][b]=1;
                    else update[a][b]=0;
                }
            }
            for (int i = 0; i < screenWidth; i = i + units) {
                for (int j = 0; j < screenHeight; j = j + units) {
                    int a=i/units,b=j/units;
                    if(update[a][b]==1) draw(g,i,j);
                    else {
                        stat[a][b]=0;
                        g.setColor(Color.white);
                        g.fillRect(i,j,units,units);
                        g.setColor(Color.black);
                        g.drawRect(i,j,units,units);

                    }
                }
                }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
    }


    @Override
    public void mousePressed(MouseEvent e) {
        x=e.getX()/units;
        y=e.getY()/units;
        x=x*units;
        y=y*units;
        k=true;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e){}
    @Override
    public void mouseReleased(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(button.isSelected()==true){
            l=false;
            repaint();
        }
        if(button.isSelected()==false){
            l=true;
        }
    }
}
