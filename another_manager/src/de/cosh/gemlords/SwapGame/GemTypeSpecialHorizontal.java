package de.cosh.gemlords.SwapGame;

public enum GemTypeSpecialHorizontal {
	TYPE_BLUE("special_blueh"),
	TYPE_GREEN("special_greenh"),
	TYPE_PURPLE("special_purpleh"),
	TYPE_RED("special_redh"),
	TYPE_WHITE("special_whiteh"),
	TYPE_YELLOW("special_yellowh"),
	TYPE_NONE("");
	private String texturePath;

	GemTypeSpecialHorizontal(final String texturePath) {
		this.texturePath = texturePath;
	}

	public String getTexturePath() {
		return texturePath;
	}
};