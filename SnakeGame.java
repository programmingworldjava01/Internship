import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements KeyListener, ActionListener {
    private final int SCALE = 20; // Size of each segment
    private final int WIDTH = 30; // Number of horizontal segments
    private final int HEIGHT = 20; // Number of vertical segments
    private ArrayList<Point> snake;
    private Point food;
    private int direction;
    private boolean running;
    private Timer timer;

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);

        snake = new ArrayList<>();
        snake.add(new Point(10, 10)); // Initial position of the snake
        direction = KeyEvent.VK_RIGHT; // Initial direction
        spawnFood();

        running = true;
        timer = new Timer(100, this); // Timer for game loop
        timer.start();
    }

    private void spawnFood() {
        Random random = new Random();
        int x = random.nextInt(WIDTH);
        int y = random.nextInt(HEIGHT);
        food = new Point(x, y);
    }

    private void move() {
        Point head = snake.get(0).getLocation();
        switch (direction) {
            case KeyEvent.VK_UP:
                head.translate(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                head.translate(0, 1);
                break;
            case KeyEvent.VK_LEFT:
                head.translate(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                head.translate(1, 0);
                break;
        }
        if (head.equals(food)) {
            snake.add(0, head);
            spawnFood();
        } else {
            snake.add(0, head);
            snake.remove(snake.size() - 1);
        }

        // Check for collision with walls or self
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT
                || snake.indexOf(head) != snake.lastIndexOf(head)) {
            running = false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Point point : snake) {
            g.setColor(Color.green);
            g.fillRect(point.x * SCALE, point.y * SCALE, SCALE, SCALE);
        }
        g.setColor(Color.red);
        g.fillRect(food.x * SCALE, food.y * SCALE, SCALE, SCALE);
        if (!running) {
            g.setColor(Color.white);
            g.drawString("Game Over", 150, 150);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN)
                && Math.abs(direction - key) != 2) {
            direction = key;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new SnakeGame(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
