import Engine.Map;
import Characters.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
	public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
		g2d.fillRect(10, 10, 50, 50);
    }
	
    public static void main(String[] args) throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
		JFrame frame = new JFrame("SSF");
        
		frame.setSize(1280, 720);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Main());
		frame.setVisible(true);
    }
}