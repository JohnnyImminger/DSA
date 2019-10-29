import javax.swing.*;

public class Gui extends JFrame {

    JPanel panel;

    public Gui(int[] resultArray, Integer dauer, int[][] resources) {
        this.setTitle("Durchlaufergebnis: "+dauer+".");

        this.panel = new MyPanel(this, resultArray, resources ,dauer);
        this.setAlwaysOnTop(true);

        this.setContentPane(this.panel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
