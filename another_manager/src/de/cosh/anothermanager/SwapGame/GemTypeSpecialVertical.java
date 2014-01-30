package de.cosh.anothermanager.SwapGame;

public enum GemTypeSpecialVertical {
	TYPE_BLUE("data/textures/special_bluev.png"),
	TYPE_GREEN("data/textures/special_greenv.png"),
	TYPE_PURPLE("data/textures/special_purplev.png"),
	TYPE_RED("data/textures/special_redv.png"),
	TYPE_WHITE("data/textures/special_whitev.png"),
	TYPE_YELLOW("data/textures/special_yellowv.png"),
	TYPE_NONE("");
	private String texturePath;

	GemTypeSpecialVertical(final String texturePath) {
		this.texturePath = texturePath;
	}

	public String getTexturePath() {
		return texturePath;
	}
};