package Characters;

import Abstract.Character;

public class Dummy extends Character {
    private boolean jumping;
    private double jumpSpeed;
    private double gravity;
    private int  initialY;

    public Dummy(int x, int y) {
        super("Dummy", x, y, 1, 7, 1);
        this.jumping = false;
        this.jumpSpeed = 0;
    }

    @Override
    public void updatePosition() {
        if (jumping) {
            jumpSpeed += weight;
            y += jumpSpeed;

            if (y >= initialY) {
                y = initialY;
                jumping = false;
                jumpSpeed = 0;
            }
        }
    }

    public void jump() {
        if (!jumping) {
            this.jumping = true;
            this.jumpSpeed = -10;
            this.initialY = y;
        }
    }
}
