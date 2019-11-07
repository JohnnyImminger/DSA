import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Gui extends JFrame {

    JPanel panel;
    public javax.swing.JScrollPane jScrollPane;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static Gui oterInstance;
    private Gui instance;

    public Gui(Integer dauer, int[][] resources, boolean shift) {
        instance = this;
        this.panel = new MyPanel(this, resources ,dauer);
        initComponents();
        this.setVisible(true);

        if(shift){
            setOpacity(1f);
            this.setTitle("shift");
            oterInstance = this;
        }else{
            setOpacity(1f);
            this.setTitle("normal");
        }

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {


            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_F1){
                    setVisible(false);

                }else if(e.getKeyCode() == KeyEvent.VK_F1 && !shift){

                }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }
            }
        });
    }

    private void initComponents() {

        jScrollPane = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.getHorizontalScrollBar().setUnitIncrement(100);
        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        jScrollPane.setViewportView(panel);

        getContentPane().add(jScrollPane);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        pack();
    }
}
