import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

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
        this.parent.setSize((dauer*10)+6,(4*maxRes*20)+46+(4*resources[0].length));//4 for the red Border-Lines
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.darkGray);//Background Color
        g.fillRect(0,0,parent.getWidth(),parent.getHeight()); //Background

        for(int c = 1; c <= resources[0].length;c++) {
            g.setColor(Color.black);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(1));
            for (int i = (this.getHeight() / this.resources[0].length * (c - 1))+3; i < (this.getHeight() / this.resources[0].length * (c))+3; i += 20) {
                g2.draw(new Line2D.Float(0, i, this.getWidth(), i));
            }
        }

        for (int i = 1 ;i <= resources[0].length;i++) {
            paintGraph(g,i);
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

    private int getMaxRes(int resultResNr){
        int runNr = 0;
        int maxresult = 0;
        for (int[] res: resources) {
            int resNr = 1;
            for (int i : res) {
                if (i > maxresult && resNr == resultResNr){
                    maxresult = i;
                }
                resNr++;
            }
            runNr++;
            if(runNr > this.dauer)break;
        }
        return maxresult;
    }

    private void paintUpRect(int x, int y, int with, int height, Graphics g){
        g.fillRect(x,this.getHeight()-height-y, with,height);
    }

    private void paintGraph(Graphics g, int  currentRow){
        if(currentRow > this.resources[0].length || currentRow == 0){
            throw new IllegalArgumentException("rowcount to large data not parent or 0");
        }

        g.setColor(Color.black);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        g2.draw(new Line2D.Float(0,(this.getHeight()/this.resources[0].length*currentRow)+2,this.getWidth(),(this.getHeight()/this.resources[0].length*currentRow)+2));


        switch (currentRow) {
            case 1:
                g.setColor(Color.BLUE);
                break;
            case 2:
                g.setColor(Color.cyan);
                break;
            case 3:
                g.setColor(Color.green);
                break;
            case 4:
                g.setColor(Color.yellow);
                break;
        }

        int runNr = 0;
        for (int[] res: resources) {
            int resNr = 1;
            for (int unUsed : res) {
                // System.out.println("at Run "+runNr+" with Recource "+resNr+": "+unUsed+" left");
                if (resNr == currentRow){

                    paintUpRect(runNr*10,(((currentRow-1)*(this.getHeight()/this.resources[0].length))),9,(getMaxRes(currentRow)-unUsed)*20,g);



                }
                resNr++;
            }
            runNr++;
            if(runNr > this.dauer)break;
        }

        g.setColor(Color.red);
        Graphics2D g3 = (Graphics2D) g;
        g3.setStroke(new BasicStroke(4));
        int r;
        switch (currentRow){
            case 1: r = 4;
                g3.draw(new Line2D.Float(0, this.getHeight() / 4 * r - getMaxRes(currentRow) * 20, this.getWidth(), this.getHeight() / 4 * r - getMaxRes(currentRow) * 20));
                break;
            case 2: r = 3;
                g3.draw(new Line2D.Float(0, this.getHeight() / 4 * r - getMaxRes(currentRow) * 20, this.getWidth(), this.getHeight() / 4 * r - getMaxRes(currentRow) * 20));
                break;
            case 3: r = 2;
                g3.draw(new Line2D.Float(0, this.getHeight() / 4 * r - getMaxRes(currentRow) * 20, this.getWidth(), this.getHeight() / 4 * r - getMaxRes(currentRow) * 20));
                break;
            case 4: r = 1;
                g3.draw(new Line2D.Float(0, this.getHeight() / 4 * r - getMaxRes(currentRow) * 20, this.getWidth(), this.getHeight() / 4 * r - getMaxRes(currentRow) * 20));
                break;
        }
    }

}

