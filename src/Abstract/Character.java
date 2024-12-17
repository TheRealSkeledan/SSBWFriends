package Abstract;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import Engine.AnimationLoader;

public abstract class Character {
    protected String name;
    public int x, y, speed;
    protected Map<String, BufferedImage[]> animations = new HashMap<>();
    protected BufferedImage currentFrame;
    protected int frameIndex = 0;
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

        loadAnimations();
        setAction("idle");
    }

    private void loadAnimations() {
        animations = AnimationLoader.loadAnimations(name);
    }

    public void setAction(String action) {
        if (!this.action.equals(action)) {
            this.action = action;
            this.frameIndex = 0; // Reset to first frame
        }
        if (animations.containsKey(action)) {
            currentFrame = animations.get(action)[frameIndex];
        } else {
            System.out.println("No animation found for action: " + action);
        }
    }

    public void updateAnimationFrame() {
        if (animations.containsKey(action)) {
            BufferedImage[] frames = animations.get(action);
            frameIndex = (frameIndex + 1) % frames.length; // Cycle frames
            currentFrame = frames[frameIndex];
        }
    }

    public BufferedImage getCurrentFrame() {
        return currentFrame;
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        setAction("walk");
    }

    public void jump() {
        if (!jumping) {
            jumping = true;
            jumpSpeed = -10;
            initialY = y;
            setAction("jump");
        }
    }

    public void updatePosition() {
        if (jumping) {
            jumpSpeed += weight;
            y += jumpSpeed;

            if (y >= initialY) {
                y = initialY;
                jumping = false;
                setAction("idle");
            }
        }
    }

    public BufferedImage getIdle() {
        return animations.containsKey("idle") ? animations.get("idle")[0] : null;
    }
}
