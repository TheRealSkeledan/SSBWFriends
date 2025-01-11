package Abstract;

import Engine.AnimationLoader;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

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

    private long lastFrameTime = 0;
    private long frameDelay = 200;

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
        animations = AnimationLoader.loadAnimations(name.toLowerCase());
    }

    public void setAction(String action) {
        if (!this.action.equals(action)) {
            this.action = action;
            this.frameIndex = 0;
        }
        updateCurrentFrame();
    }

    private void updateCurrentFrame() {
        if (animations.containsKey(action)) {
            currentFrame = animations.get(action)[frameIndex];
        } else {
            System.out.println("No animation found for action: " + action);
        }
    }

    public void updateAnimationFrame() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= frameDelay) {
            if (animations.containsKey(action)) {
                BufferedImage[] frames = animations.get(action);
                frameIndex = (frameIndex + 1) % frames.length;
                updateCurrentFrame();
            }
            lastFrameTime = currentTime;
        }
    }

    public BufferedImage getCurrentFrame() {
        return currentFrame;
    }

    public void move(int dx, int dy) {
        if (x + dx >= -110 && x + dx <= 1390 - getWidth()) {
            x += dx;
            setAction("walk");
        }
        if (y + dy >= 0 && y + dy <= 720 - getHeight()) {
            y += dy;
            setAction("walk");
        }
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return currentFrame != null ? currentFrame.getWidth() : 0;
    }
    
    public int getHeight() {
        return currentFrame != null ? currentFrame.getHeight() : 0;
    }

    public void changeHP(int amt) {
        if (HP + amt >= 0 && HP + amt <= 100) {
            HP += amt;
        }
    }
    
    public int getHP() {
        return HP;
    }

    public void changeKP(int amt) {
        if (KP + amt >= 0 && KP + amt <= 100) {
            KP += amt;
        }
    }

    public int getKP() {
        return KP;
    }
}
