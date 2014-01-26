package de.cosh.anothermanager.SwapGame;

public enum GemType {
	TYPE_BLUE("data/textures/ball_blue.png"),
	TYPE_GREEN("data/textures/ball_green.png"),
	TYPE_PURPLE("data/textures/ball_purple.png"),
	TYPE_RED("data/textures/ball_red.png"),
	TYPE_WHITE("data/textures/ball_white.png"),
	TYPE_YELLOW("data/textures/ball_yellow.png"),
	TYPE_NONE("");

	private String texturePath;

	GemType(final String texturePath) {
		this.texturePath = texturePath;
	}

	public String getTexturePath() {
		return texturePath;
	}
};