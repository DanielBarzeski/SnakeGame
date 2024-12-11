import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window() {
        setTitle("SNAKE GAME");
        getContentPane().setBackground(Color.cyan);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel display = new JPanel();
        display.setLayout(new FlowLayout());
        display.setBackground(Color.cyan);
        display.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT + 50));
        JPanel menu = new JPanel();
        menu.setBackground(Color.orange);
        menu.setPreferredSize(new Dimension(Game.WIDTH, 40));
        display.add(menu);
        display.add(new GameDisplay(menu));
        add(display);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
