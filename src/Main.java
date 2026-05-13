import com.spacewar.logic.GameLoop;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Space War");
        GameLoop gameLoop = new GameLoop();

        ventana.add(gameLoop);
        ventana.pack();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setVisible(true);

        gameLoop.iniciarHilo();
    }
}