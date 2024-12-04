package Engine;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {
    public static BufferedImage loadImageAsRGB(String filePath) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(filePath));

        if (originalImage.getType() != BufferedImage.TYPE_INT_RGB) {
            BufferedImage convertedImage = new BufferedImage(
                    originalImage.getWidth(),
                    originalImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D g = convertedImage.createGraphics();

            g.drawImage(originalImage, 0, 0, null);
            g.dispose();

            return convertedImage;
        }

        return originalImage;
    }
}
