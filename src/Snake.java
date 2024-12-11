import java.awt.*;
import java.util.LinkedList;

public class Snake {
    private final LinkedList<Point> body;
    private Point direction;
    private boolean grow;

    public Snake() {
        body = new LinkedList<>();
        body.add(new Point(5, 5));
        direction = new Point(1, 0); // right direction
    }

    public Point getHead() {
        return body.getFirst();
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public Point getNextHead() {
        return new Point(getHead().x + direction.x, getHead().y + direction.y);
    }

    public void move(Point head) {
        body.addFirst(head);
        if (!grow)
            body.removeLast();
        else
            grow = false;
    }

    public void setDirection(int x, int y) {
        if (x != -direction.x || y != -direction.y)
            direction = new Point(x, y);
    }

    public void grow() {
        grow = true;
    }

    public void draw(Graphics g) {
        int i = body.size() - 1;
        while (i >= 0) {
            Point point = body.get(i);
            g.setColor(Color.GREEN);
            if (point == getHead())
                g.setColor(Color.GREEN.darker());
            g.fillOval(point.x * Game.CELL_SIZE, point.y * Game.CELL_SIZE, Game.CELL_SIZE-1, Game.CELL_SIZE-1);
            g.setColor(Color.BLACK);
            g.drawOval(point.x * Game.CELL_SIZE, point.y * Game.CELL_SIZE, Game.CELL_SIZE-1, Game.CELL_SIZE-1);
            i--;
        }
    }

}