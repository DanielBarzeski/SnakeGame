import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Board {
    private final int cellWidth = Game.WIDTH / Game.CELL_SIZE, cellHeight = Game.HEIGHT /Game. CELL_SIZE;
    private final Snake snake;
    private Point foodPoint;
    private int score;

    public Board() {
        snake = new Snake();
        generateFoodPoint();
        score = 0;
    }

    public void moveSnake() {
        Point head = snake.getNextHead();
        if (snakeDirectionIsValid(head)) {
            // snake.setHead(head);
            snake.move(head);
            // Check if the snake eats the food point
            if (head.equals(foodPoint)) {
                snake.grow();
                score += 10; // Increase score for eating the special point
                generateFoodPoint(); // Generate a new special point
            }

        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.orange);
        for (int i = 0; i < cellHeight; i++) {
            for (int j = 0; j < cellWidth; j++) {
                g.drawRect(j * Game.CELL_SIZE,
                        i * Game.CELL_SIZE,
                        Game.CELL_SIZE-1, Game.CELL_SIZE-1);
            }
        }
        snake.draw(g);
        if (foodPoint != null) {
            g.setColor(Color.RED);
            Rectangle rect = new Rectangle(
                    foodPoint.x * Game.CELL_SIZE + Game.CELL_SIZE / 4,
                    foodPoint.y * Game.CELL_SIZE + Game.CELL_SIZE / 4,
                    Game.CELL_SIZE / 2-1, Game.CELL_SIZE / 2-1);
            g.fillOval(rect.x,rect.y,rect.width,rect.height);
            g.setColor(Color.black);
            g.drawOval(rect.x,rect.y,rect.width,rect.height);
        }

        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 10);
        if (Game.isFINISHED()) {
            g.setFont(new Font("Arial",Font.BOLD,24));
            if (score >= 10 * cellWidth * cellHeight)
                g.drawString("YOU WON!", Game.WIDTH / 2 - 70, Game.HEIGHT / 2 - 2);
            else
                g.drawString("GAME OVER!", Game.WIDTH / 2 - 70, Game.HEIGHT / 2 - 2);
        }
    }

    public void setSnakeDirection(int x, int y) {
        snake.setDirection(x, y);
    }

    private boolean snakeDirectionIsValid(Point head) {
        if (head != null) {
            // Check collision with the walls (wrap around)
            if (head.x < 0) head.x = cellWidth - 1;
            if (head.x >= cellWidth) head.x = 0;
            if (head.y < 0) head.y = cellHeight - 1;
            if (head.y >= cellHeight) head.y = 0;

            // check if collides
            for (int i = 1; i < snake.getBody().size() - 1; i++) {
                if (head.equals(snake.getBody().get(i))) {
                    Game.END(); // End the game if it collides with itself
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private synchronized void generateFoodPoint() {
        LinkedList<Point> freePoints = new LinkedList<>();
        for (int x = 0; x < cellWidth; x++) {
            for (int y = 0; y < cellHeight; y++) {
                Point point = new Point(x, y);
                if (!snake.getBody().contains(point)) {
                    freePoints.add(point);
                }
            }
        }
        if (freePoints.isEmpty())
            foodPoint = new Point(-1,-1);
        else
            foodPoint = freePoints.get(new Random().nextInt(freePoints.size()));
    }
}