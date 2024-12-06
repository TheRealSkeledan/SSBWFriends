package Abstract;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Character {
    protected String name;
    protected int x, y;
    protected BufferedImage idleImage;
    protected int damage;
    protected double speed;
    protected double weight;

    public Character(String name, int x, int y, int damage, double speed, double weight) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.speed = speed;
        this.weight = weight;
        
        try {
            this.idleImage = ImageIO.read(new File("assets/images/characters/" + name + "/idle.png"));
        } catch (IOException e) {
            e.printStackTrace(); // Handle image loading errors
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

    public void moveX(int amt) {
        x += amt;
    }

    public void moveY(int amt) {
        y += amt;
    }

    // Method to get the idle image
    public BufferedImage getIdle() {
        return idleImage;
    }

    // Abstract method for updating position (to be implemented by subclasses)
    public abstract void updatePosition();
}
