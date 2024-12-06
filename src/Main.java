import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Characters.Dummy;
import Engine.Map;

public class Main extends JPanel {
    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;

    boolean jumping = false;

    private Dummy dummy;

    public Main() {
        dummy = new Dummy(100, 100);

        this.setFocusable(true);
        this.addKeyListener(new CharacterMovementListener());
    }

    // Paint the map and the character
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(!jumping) {
            while(dummy.getY() != 300) {
                if(dummy.getY() < 300) 
                    dummy.moveY(1);
                else   
                    dummy.moveY(-1);
            }
        }

        Map.drawMap(SCREEN_WIDTH, SCREEN_HEIGHT, 1, g);

        dummy.updatePosition();

        g.drawImage(dummy.getIdle(), dummy.getX(), dummy.getY(), this);

        long nanoseconds = System.nanoTime();
        long dif = System.nanoTime() - nanoseconds;
        double fps = 1000000000.0 / dif; 
        g.setColor(Color.red); 
        g.drawString("FPS: " + Integer.toString((int) fps), 10, 10);
    }

    private class CharacterMovementListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            int dx = 0, dy = 0;

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> dx = -5;
                case KeyEvent.VK_RIGHT -> dx = 5;
                case KeyEvent.VK_UP -> {
                    dummy.jump();
                    jumping = true;
                }
            }

            if (dx != 0 || dy != 0) {
                dummy.moveX(dx);
                dummy.moveY(dy);
            }

            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_UP -> {
                    jumping = false;
                }
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {}
    }

    // Main method to launch the game
    public static void main(String[] args) {
        JFrame frame = new JFrame("SSF");
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new Main());
        frame.setVisible(true);
    }
}
