import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Gui extends JFrame {

    JPanel panel;
    public javax.swing.JScrollPane jScrollPane;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Gui(int[] resultArray, Integer dauer, int[][] resources, float opacity) {
        this.panel = new MyPanel(this, resultArray, resources ,dauer);
        initComponents();
        this.setVisible(true);
        setOpacity(opacity);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (opacity != 1){
                    if(e.getKeyCode() == KeyEvent.VK_F1)System.exit(0);
                }else {
                    if(e.getKeyCode() == KeyEvent.VK_F2)System.exit(0);
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
