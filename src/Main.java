import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import Characters.Dummy;
import Engine.Map;

public class Main extends JPanel {
    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;

    private boolean[] keys = new boolean[4];
    private boolean isFacingRight = true;

    private Dummy dummy;

    private Timer timer;

    private long lastTime = System.nanoTime();
    private int fps = 0;
    private int frameCount = 0;

    public Main() {
        dummy = new Dummy(100, 300);
        dummy.setImage("idle");
        Map.setName("testStage");

        this.setFocusable(true);
        this.addKeyListener(new Keyboard());
        timer = new Timer(9, new TimerListener());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        move();
        dummy.updatePosition();

        Graphics2D g2d = (Graphics2D) g;

        Map.drawStage(g2d);

        if (isFacingRight) {
            g2d.drawImage(dummy.getIdle(), dummy.getX(), dummy.getY(), this);
        } else {
            g2d.drawImage(dummy.getIdle(), dummy.getX() + dummy.getWidth(), dummy.getY(), -dummy.getWidth(), dummy.getHeight(), this);
        }

        g2d.setTransform(g2d.getDeviceConfiguration().getDefaultTransform());

        long currentTime = System.nanoTime();
        frameCount++;
        if (currentTime - lastTime >= 1000000000) {
            fps = frameCount;
            frameCount = 0;
            lastTime = currentTime;
        }

        g.setColor(Color.red);
        g.drawString("FPS: " + fps, 10, 10);
    }


    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            move();
            repaint();
        }
    }

    private class Keyboard implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyChar()) {
                case 'w' -> keys[0] = true;
                case 'a' -> {
                    keys[1] = true;
                    isFacingRight = false;
                }
                case 's' -> keys[2] = true;
                case 'd' -> {
                    keys[3] = true;
                    isFacingRight = true;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyChar()) {
                case 'w' -> keys[0] = false;
                case 'a' -> keys[1] = false;
                case 's' -> keys[2] = false;
                case 'd' -> keys[3] = false;
            }
        }
    }

    public void move() {
        if (keys[0]) {
            dummy.jump();
        }
        if (keys[1] && dummy.x > 0) {
            dummy.x -= 1 * dummy.speed;
        }
        if (keys[2]) {
            dummy.y += 1 * dummy.weight;
        }
        if (keys[3] && dummy.x + dummy.getWidth() < SCREEN_WIDTH) {
            dummy.x += 1 * dummy.speed;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SSF");
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new Main());
        frame.setVisible(true);
    }
}
