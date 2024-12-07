package Abstract;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Character {
    protected String name;
    public int x, y;
    protected BufferedImage image;
    protected int damage;
    public double speed, weight;
    protected String action;

    public Character(String name, int x, int y, int damage, double speed, double weight) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.speed = speed;
        this.weight = weight;
        
        try {
            this.image = ImageIO.read(new File("assets/images/characters/" + name + "/idle.png"));
        } catch (IOException e) {
            System.out.println("Path couldn't find the file " + action + ".png");
        }
    }

    public Character() {
        
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getWidth() {
        return image != null ? image.getWidth() : 0;
    }

    public int getHeight() {
        return image != null ? image.getHeight() : 0;
    }

    public BufferedImage getIdle() {
        return image;
    }

    public void setImage(String action) {
        this.action = action;
    }

    public abstract void updatePosition();
}
