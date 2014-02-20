package de.cosh.anothermanager.SwapGame;

public enum GemTypeSpecialVertical {
	TYPE_BLUE("special_bluev"),
	TYPE_GREEN("special_greenv"),
	TYPE_PURPLE("special_purplev"),
	TYPE_RED("special_redv"),
	TYPE_WHITE("special_whitev"),
	TYPE_YELLOW("special_yellowv"),
	TYPE_NONE("");
	private String texturePath;

	GemTypeSpecialVertical(final String texturePath) {
		this.texturePath = texturePath;
	}

	public String getTexturePath() {
		return texturePath;
	}
};