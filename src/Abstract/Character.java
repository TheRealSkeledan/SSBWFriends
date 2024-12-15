// Character.java

package Abstract;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public abstract class Character {
    protected String name;
    public int x, y, speed;
    protected Map<String, BufferedImage> images = new HashMap<>();
    protected BufferedImage image;
    protected int damage;
    public double weight;
    protected String action = "idle";
    protected boolean jumping = false;
    protected double jumpSpeed = 0;
    protected int initialY;
    protected int KP = 0, HP = 100;

    public Character(String name, int x, int y, int damage, int strength, int resistance, int speed, double weight) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.speed = speed;
        this.weight = weight;

        loadImages();
        setImage("idle");
    }

    private void loadImages() {
        String[] actions = {"idle", "jump", "crouch"};
        for (String act : actions) {
            try {
                BufferedImage img = ImageIO.read(new File("assets/images/characters/" + name + "/" + act + ".png"));
                images.put(act, img);
            } catch (IOException e) {
                System.out.println("Failed to load " + act + ".png for " + name);
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int x, int y) {
        if(action.equals("crouch"))
            setImage("idle");

        if (this.x + x >= -88 && this.x + x <= 1350 - getWidth())
            this.x += x;

        if (this.y + y >= 0 && this.y + y <= 720)
            this.y += y;
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
        if (images.containsKey(action)) {
            this.image = images.get(action);
        } else {
            System.out.println("Image for action '" + action + "' not found!");
        }
    }

    public void updatePosition() {
        if (jumping) {
            jumpSpeed += weight;
            y += jumpSpeed;

            if (y >= initialY) {
                y = initialY;
                jumping = false;
                jumpSpeed = 0;
                setImage("idle");
            }
        }
    }

    public void jump() {
        if (!jumping) {
            this.jumping = true;
            this.jumpSpeed = -10;
            this.initialY = y;
            setImage("jump");
        }
    }

    public void changeHP(int amt) {
        if(HP + amt >= 0 && HP + amt <= 100)
            HP = HP + amt;
    }
    
    public int getHP() {
        return HP;
    }

    public void changeKP(int amt) {
        if(KP + amt >= 0 && KP + amt <= 100)
            KP = KP + amt;
    }

    public int getKP() {
        return KP;
    }

    public void AI() {};
}