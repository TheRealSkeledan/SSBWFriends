// UI.java

package Engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class UI {
    private static BufferedImage HPBar, hpAmount, kpAmount, KPBar, backBar;

    public static void create() {
        try {
            HPBar = ImageIO.read(new File("assets/images/ui/healthBar.png"));
            KPBar = ImageIO.read(new File("assets/images/ui/kpbar.png"));
            hpAmount = ImageIO.read(new File("assets/images/ui/healthGrad.png"));
            kpAmount = ImageIO.read(new File("assets/images/ui/kpGrad.png"));
            backBar = ImageIO.read(new File("assets/images/ui/backGrad.png"));
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
        g.drawImage(backBar, 0, 0, 500, 50, null);
        g.drawImage(hpAmount, 0, 0, 50 * health/10, 50, null);
        g.drawImage(getHPBar(), 0, 0, 500, 50, null);
        
        g.drawImage(backBar, 0, 600, 300, 100, null);
        g.drawImage(kpAmount, 0, 600, 3 * killPower, 100, null);
        g.drawImage(getKPBar(), 0, 600, null);
    }
}
