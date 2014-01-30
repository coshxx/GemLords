package de.cosh.anothermanager.SwapGame;

public enum GemTypeSpecialHorizontal {
	TYPE_BLUE("data/textures/special_blueh.png"),
	TYPE_GREEN("data/textures/special_greenh.png"),
	TYPE_PURPLE("data/textures/special_purpleh.png"),
	TYPE_RED("data/textures/special_redh.png"),
	TYPE_WHITE("data/textures/special_whiteh.png"),
	TYPE_YELLOW("data/textures/special_yellowh.png"),
	TYPE_NONE("");
	private String texturePath;

	GemTypeSpecialHorizontal(final String texturePath) {
		this.texturePath = texturePath;
	}

	public String getTexturePath() {
		return texturePath;
	}
};