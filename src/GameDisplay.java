import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameDisplay extends JPanel implements Runnable {
    private final JButton pause, restart;
    private Board controlBoard;
    private final JPanel menu;

    public GameDisplay(JPanel menu) {
        this.menu = menu;
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setBackground(Color.yellow);
        restart = new JButton("restart");
        pause = new JButton("pause");
        pause.addActionListener(e -> {
            if (Game.isPAUSED()) {
                pause.setText("pause");
                Game.CONTINUE();
            } else {
                pause.setText("continue");
                Game.PAUSE();
            }
        });
        menu.add(pause);
        controlBoard = new Board();
        new Thread(this).start();
        restart.addActionListener(e -> {
            controlBoard = new Board();
            Game.START();
            menu.remove(restart);
            menu.add(pause);
            menu.revalidate();
            menu.repaint();
        });
        Game.START();
        setupKeyBindings();
    }

    private void updateGame() {
        controlBoard.moveSnake();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        controlBoard.draw(g);
    }

    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        Object[][] keyActions = {
                {"LEFT", "moveLeft", (Runnable) () -> controlBoard.setSnakeDirection(-1, 0)},
                {"RIGHT", "moveRight", (Runnable) () -> controlBoard.setSnakeDirection(1, 0)},
                {"UP", "moveUp", (Runnable) () -> controlBoard.setSnakeDirection(0, -1)},
                {"DOWN", "moveDown", (Runnable) () -> controlBoard.setSnakeDirection(0, 1)}
        };

        for (Object[] keyAction : keyActions) {
            String key = (String) keyAction[0];
            String actionName = (String) keyAction[1];
            Runnable action = (Runnable) keyAction[2];
            inputMap.put(KeyStroke.getKeyStroke(key), actionName);
            actionMap.put(actionName, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!Game.isFINISHED() && !Game.isPAUSED()) {
                        action.run();
                        repaint();
                    }
                }
            });
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                System.out.println("message: " + e.getMessage());
            }
            if (!Game.isFINISHED() && !Game.isPAUSED())
                updateGame();
            else if (Game.isFINISHED()) {
                menu.remove(pause);
                menu.add(restart);
                menu.revalidate();
                menu.repaint();
            }
        }
    }
}