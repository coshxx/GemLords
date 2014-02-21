package de.cosh.gemlords.SwapGame;

public enum GemType {
	TYPE_BLUE("ball_blue"),
	TYPE_GREEN("ball_green"),
	TYPE_PURPLE("ball_purple"),
	TYPE_RED("ball_red"),
	TYPE_WHITE("ball_white"),
	TYPE_YELLOW("ball_yellow"),
	TYPE_NONE("");

	private String texturePath;

	GemType(final String texturePath) {
		this.texturePath = texturePath;
	}

	public String getTexturePath() {
		return texturePath;
	}
};