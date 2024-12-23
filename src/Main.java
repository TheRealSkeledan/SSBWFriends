// Main.java

import Characters.Tom;
import Engine.Map;
import Engine.UI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;

    private boolean[] keys = new boolean[4];
    private boolean isFacingRight = true;

    private Tom dummy;

    private long lastTime = System.nanoTime();
    private int fps = 0;
    private int frameCount = 0;

    private static final int TARGET_FPS = 300;
    private static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

    private ScheduledExecutorService executorService;

    public Main() {
        dummy = new Tom(100, 300);
        Map.setName("testStage");
        UI.create();

        this.setFocusable(true);
        this.addKeyListener(new Keyboard());

        setDoubleBuffered(true);

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::gameLoop, 0, 1000000000 / TARGET_FPS, TimeUnit.NANOSECONDS);
    }

    private void gameLoop() {
        move();
        dummy.updatePosition();
        repaint();

        long now = System.nanoTime();
        frameCount++;
        if (now - lastTime >= 1000000000) {
            fps = frameCount;
            frameCount = 0;
            lastTime = now;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        Map.drawStage(g);

        if (isFacingRight) {
            g.drawImage(dummy.getIdle(), dummy.getX(), dummy.getY(), this);
        } else {
            g.drawImage(dummy.getIdle(), dummy.getX() + dummy.getWidth(), dummy.getY(), -dummy.getWidth(), dummy.getHeight(), this);
        }

        UI.drawUI(dummy.getHP(), dummy.getKP(), g);

        g.setColor(Color.RED);
        g.drawString("FPS: " + fps, 10, 10);
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
                case 's' -> {
                    keys[2] = false;
                    dummy.setAction("idle");
                }
                case 'd' -> keys[3] = false;
            }
        }

    }

    public void move() {
        if (keys[0]) {
            dummy.jump();
        }
        if (keys[1]) {
            dummy.move(-dummy.speed, 0);
        }
        if (keys[2]) {
            dummy.setAction("crouch");
        }
        if (keys[3]) {
            dummy.move(dummy.speed, 0);
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("SSF");
            frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new Main());
            frame.setVisible(true);
        });
    }
}