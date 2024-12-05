package Abstract;

public abstract class Character {
	protected String name;
	protected int hp, dmg;
	protected double x, y;
	protected double speed;
	protected String image;

	public Character(String name, int hp, int dmg, double speed, double x, double y) {
		this.name = name;
		this.hp = hp;
		this.dmg = dmg;
		this.speed = speed;
		this.x = x;
		this.y = y;

		image = "assets/characters/" + name;
	}

	public String getIdle() {
		return image + "idle.png";
	}

	public String getDamaged() {
		return image + "dmg.png";
	}

	public String getAttack() {
		return image + "atk.png";
	}

	public String getSpecial() {
		return image + 
	}
}