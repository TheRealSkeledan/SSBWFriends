package Engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class UI {
    private static BufferedImage HPBar, KPBar;

    public static void create() {
        try {
            BufferedImage HPBar = ImageIO.read(new File("assets/images/ui/healthBar.png"));
            BufferedImage KPBar = ImageIO.read(new File("assets/images/ui/kpbar.png"));
        } catch (IOException e) {
            System.out.println("Failed to load the images!");
        }
    }

    public static BufferedImage getHPBar() {
        return HPBar;
    }

    public static BufferedImage getKPBar() {
        return KPBar;
    }

    public static void drawUI(int health, int killPower, Graphics g) {
        g.setColor(new Color(43, 255, 167));
        g.fillRect(0, 0, 15 * health/10, 30);
        g.drawImage(getHPBar(), 300, 300, null);
        
        g.fillRect(0, 600, 3 * killPower, 100);
        g.drawImage(getKPBar(), 0, 600, null);
    }
}