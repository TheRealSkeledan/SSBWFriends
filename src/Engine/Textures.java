package Engine;

import Rooms.Room;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Textures {

    public static BufferedImage floor;
    public static BufferedImage wall;
    public static BufferedImage door;
    public static BufferedImage RedSun;
    public static BufferedImage pan;
    public static BufferedImage coin;
    public static int[] coinsAmt = {5, 10, 20, 100, 200, 500};
    
    public static void init() throws IOException{
        if(Room.getFloor() < 50) {
            wall = ImageIO.read(new File("assets/images/textures/hotelWall.png"));
            floor = ImageLoader.loadImageAsRGB("assets/images/textures/hotelFloor.png");
        } if(Room.getFloor() > 50) {
            wall = ImageIO.read(new File("assets/images/textures/officeWall.png"));
            floor = ImageLoader.loadImageAsRGB("assets/images/textures/officeFloor.png");
        }
		
        door = ImageLoader.loadImageAsRGB("assets/images/textures/door.png");
        RedSun = ImageLoader.loadImageAsRGB("assets/images/entities/dupe.png");
        pan = ImageLoader.loadImageAsRGB("assets/images/textures/pan.jpg");
        coin = ImageLoader.loadImageAsRGB("assets/images/textures/" + coinsAmt[(int)(Math.random() * coinsAmt.length)] + "coin.png");
    }
}