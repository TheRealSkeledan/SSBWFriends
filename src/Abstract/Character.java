package Abstract;

import java.awt.image.BufferedImage;

public abstract class Entity {
	protected String name;
	protected int hp, dmg;
	protected double x, y;
	protected double speed;
	protected BufferedImage image;

	public Character(String name, int hp, int dmg, double speed) {
		this.name = name;
		this.hp = hp;
		this.dmg = dmg;
		this.speed = speed;
	}
}