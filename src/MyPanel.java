import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Arrays;
import java.util.Collections;

public class MyPanel extends JPanel {

    private JFrame parent;
    int[][] resources;
    int[] resultArray;
    int dauer;
    int maxRes;

    public MyPanel(JFrame parent, int[] resultArray, int[][] resources , int dauer){
        this.parent = parent;
        this.resources = resources;
        this.resultArray = resultArray;
        this.dauer = dauer;
        this.parent.setResizable(false);
        this.maxRes = this.getMaxRes();
        this.parent.setSize((dauer*10)+6,(4*maxRes*20)+46+4);//4 for the red Border-Lines
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.darkGray);//Background Color
        g.fillRect(0,0,parent.getWidth(),parent.getHeight()); //Background
        // System.out.println(String.valueOf(this.getWidth()-dauer*10) + ";" + String.valueOf(this.getHeight()-4*14*20));  //removable calc of size differents in pixel


        for(int i = 0; i < resources[0].length; i++){
            switch (i){
                case 0: g.setColor(Color.BLUE);
                    break;
                case 1: g.setColor(Color.cyan);
                    break;
                case 2: g.setColor(Color.green);
                    break;
                case 3: g.setColor(Color.yellow);
                    break;
            }
            g.fillRect(0,this.getHeight()/4*i,this.getWidth(),this.getHeight()/4);
            g.setColor(Color.red);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(4));
            g2.draw(new Line2D.Float(0,(this.getHeight()/4*i)+i,this.getWidth(),(this.getHeight()/4*i)+i));
            //g.drawLine(0,(this.getHeight()/4*i),this.getWidth(),(this.getHeight()/4*i));
        } // draw max Lines (resCap) and BG colors

        int runNr = 0;
        for (int[] res: resources) {
            int resNr = 0;
            for (int used : res) {
                //System.out.println("at Run "+runNr+" with Recource "+resNr+": "+used);
                g.setColor(Color.darkGray);
                g.fillRect(runNr*10,(((maxRes*20)+2)*resNr),10,used*20);
                resNr++;
            }
            runNr++;
            if(runNr > this.dauer)break;
        }

    }

    private int getMaxRes(){
        int runNr = 0;
        int maxresult = 0;
        for (int[] res: resources) {
            for (int i : res) {
               if (i > maxresult){
                   maxresult = i;
               }
            }
            runNr++;
            if(runNr > this.dauer)break;
        }
        return maxresult;
    }
}
