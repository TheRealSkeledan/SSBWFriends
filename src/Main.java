// Main.java

import Characters.Red;
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

    private boolean[] keys = new boolean[12];
    private boolean p1isFacingRight = true;
    private boolean p2isFacingRight = false;

    private Red p1;
    private Red p2;

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
        p1 = new Red(40, 300);
        p2 = new Red(800, 300);
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
        moveP1();
        moveP2();
        p1.updatePosition();
        p1.updateAnimationFrame();

        p2.updatePosition();
        p2.updateAnimationFrame();
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
        if (p1isFacingRight) {
            g2d.drawImage(p1.getCurrentFrame(), p1.getX(), p1.getY(), this);
        } else {
            g2d.drawImage(p1.getCurrentFrame(), p1.getX() + p1.getWidth(), p1.getY(),
                          -p1.getWidth(), p1.getHeight(), this);
        }

        if (p2isFacingRight) {
            g2d.drawImage(p2.getCurrentFrame(), p2.getX(), p2.getY(), this);
        } else {
            g2d.drawImage(p2.getCurrentFrame(), p2.getX() + p2.getWidth(), p2.getY(),
                          -p2.getWidth(), p2.getHeight(), this);
        }

        UI.drawUI(p1.getHP(), p1.getKP(), true, g2d, p1.name);
        UI.drawUI(p2.getHP(), p2.getKP(), false, g2d, p2.name);

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
                // Player 1
                case 'w' -> keys[0] = true;
                case 'a' -> {
                    keys[1] = true;
                    p1isFacingRight = false;
                }
                case 'e' -> keys[2] = true;
                case 'd' -> {
                    keys[3] = true;
                    p1isFacingRight = true;
                }
                case 'f' -> {
                    keys[4] = true;
                }
                case 'c' -> {
                    keys[5] = true;
                }
                case 'q' -> {
                    keys[6] = true;
                }

                // Player 2
                case 'i' -> keys[7] = true;
                case 'j' -> {
                    keys[8] = true;
                    p2isFacingRight = false;
                }
                case 'o' -> keys[9] = true;
                case 'l' -> {
                    keys[10] = true;
                    p2isFacingRight = true;
                }
                case 'h' -> {
                    keys[11] = true;
                }
                case 'n' -> {
                    keys[12] = true;
                }
                case 'u' -> {
                    keys[13] = true;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyChar()) {
                // Player 1
                case 'w' -> keys[0] = false;
                case 'a' -> keys[1] = false;
                case 'f' -> keys[2] = false;
                case 'd' -> keys[3] = false;

                // Player 2
                case 'i' -> keys[4] = false;
                case 'j' -> keys[5] = false;
                case 'o' -> keys[6] = false;
                case 'l' -> keys[7] = false;
            }
        }
    }

    public void moveP1() {
        if (keys[0]) {
            p1.jump();
        }
        if(!keys[2]) {
            if (keys[1]) {
                p1.move(-p1.speed);
            }
            
            if (keys[3]) {
                p1.move(p1.speed);
            }
        }
        if (keys[2]) {
            p1.defend();
        }

        resetAnimP1();
    }

    public void resetAnimP1() {
        if(!keys[1] && !keys[2] && !keys[3] && !p1.jumping) {
            p1.setAction("idle");
        }
    }

    public void moveP2() {
        if(keys[4]) {
            p2.jump();
        }
        if(!keys[6]) {
            if(keys[5] && !keys[6]) {
                p2.move(-p2.speed);
            }
            if(keys[7] && !keys[6]) {
                p2.move(p2.speed);
            }
        }
        
        if(keys[6]) {
            p2.defend();
        }
        
        resetAnimP2();
    }

    public void resetAnimP2() {
        if(!keys[4] && !keys[5] && !keys[6] && !keys[7] && !p2.jumping) {
            p2.setAction("idle");
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
