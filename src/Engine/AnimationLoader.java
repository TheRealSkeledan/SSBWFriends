package Engine;

import javax.imageio.ImageIO;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AnimationLoader {
    public static Map<String, BufferedImage[]> loadAnimations(String characterName) {
        Map<String, BufferedImage[]> animations = new HashMap<>();
        try {
            // File paths
            String basePath = "assets/images/characters/" + characterName + "/";
            File xmlFile = new File(basePath + characterName + ".xml");
            BufferedImage spriteSheet = ImageIO.read(new File(basePath + characterName + ".png"));

            // Initialize StAX parser
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(new java.io.FileInputStream(xmlFile));

            String currentAction = null;
            BufferedImage[] frames = null;
            int frameIndex = 0;

            while (reader.hasNext()) {
                int event = reader.next();

                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        if (reader.getLocalName().equals("animation")) {
                            currentAction = reader.getAttributeValue(null, "name");
                            int totalFrames = Integer.parseInt(reader.getAttributeValue(null, "frames"));
                            frames = new BufferedImage[totalFrames];
                            frameIndex = 0;
                        } else if (reader.getLocalName().equals("frame")) {
                            // Read frame attributes
                            int x = Integer.parseInt(reader.getAttributeValue(null, "x"));
                            int y = Integer.parseInt(reader.getAttributeValue(null, "y"));
                            int width = 300;  // Fixed frame size
                            int height = 300;

                            if (frames != null) {
                                frames[frameIndex++] = spriteSheet.getSubimage(x, y, width, height);
                            }
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        if (reader.getLocalName().equals("animation") && currentAction != null && frames != null) {
                            animations.put(currentAction, frames);
                        }
                        break;
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error loading animations for " + characterName);
            e.printStackTrace();
        }
        return animations;
    }
}
