// Main.java

import Characters.Tom;
import Engine.Map;
import Engine.UI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

    private Lorz dummy;

    private long lastTime = System.nanoTime();
    private int fps = 0;
    private int frameCount = 0;

    private static final int TARGET_FPS = 300;
    private static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

    private ScheduledExecutorService executorService;

    private int renderWidth = SCREEN_WIDTH;
    private int renderHeight = SCREEN_HEIGHT;
    private int renderXOffset = 0;
    private int renderYOffset = 0;

    public Main() {
        dummy = new Lorz(100, 300);
        Map.setName("dam");
        UI.create();

        this.setFocusable(true);
        this.addKeyListener(new Keyboard());

        setDoubleBuffered(true);

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::gameLoop, 0, OPTIMAL_TIME, TimeUnit.NANOSECONDS);

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                adjustViewport();
            }
        });
    }

    private void adjustViewport() {
        Dimension size = getSize();
        double aspectRatio = (double) SCREEN_WIDTH / SCREEN_HEIGHT;
        double windowRatio = (double) size.width / size.height;

        if (windowRatio > aspectRatio) {
            renderHeight = size.height;
            renderWidth = (int) (size.height * aspectRatio);
            renderXOffset = (size.width - renderWidth) / 2;
            renderYOffset = 0;
        } else {
            renderWidth = size.width;
            renderHeight = (int) (size.width / aspectRatio);
            renderXOffset = 0;
            renderYOffset = (size.height - renderHeight) / 2;
        }
    }

    private void gameLoop() {
        move();
        dummy.updatePosition();
        dummy.updateAnimationFrame();
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
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.translate(renderXOffset, renderYOffset);
        g2d.scale((double) renderWidth / SCREEN_WIDTH, (double) renderHeight / SCREEN_HEIGHT);

        Map.drawStage(g2d);
        if (isFacingRight) {
            g2d.drawImage(dummy.getCurrentFrame(), dummy.getX(), dummy.getY(), this);
        } else {
            g2d.drawImage(dummy.getCurrentFrame(), dummy.getX() + dummy.getWidth(), dummy.getY(),
                          -dummy.getWidth(), dummy.getHeight(), this);
        }

        UI.drawUI(dummy.getHP(), dummy.getKP(), g2d);

        g2d.setColor(Color.RED);
        g2d.drawString("FPS: " + fps, 10, 10);

        g2d.dispose();
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
                case 'f' -> keys[2] = true;
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
                case 'a' -> {
                    keys[1] = false;
                }
                case 'f' -> keys[2] = false;
                case 'd' -> {
                    keys[3] = false;
                }
            }
        }
    }

    public void move() {
        if (keys[0]) {
            dummy.jump();
        }
        if (keys[1] && !keys[2]) {
            dummy.move(-dummy.speed);
        }
        if (keys[2]) {
            dummy.defend();
        }
        if (keys[3] && !keys[2]) {
            dummy.move(dummy.speed);
        }

        resetAnim();
    }

    public void resetAnim() {
        if(!keys[1] && !keys[2] && !keys[3] && !dummy.jumping) {
            dummy.setAction("idle");
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
