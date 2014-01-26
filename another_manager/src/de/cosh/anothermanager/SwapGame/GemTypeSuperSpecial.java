package de.cosh.anothermanager.SwapGame;

public enum GemTypeSuperSpecial {
	TYPE_SPECIAL_5("data/textures/special_5.png"),
	TYPE_NONE("");

	private String texturePath;

	GemTypeSuperSpecial(final String texturePath) {
		this.texturePath = texturePath;
	}

	public String getTexturePath() {
		return texturePath;
	}
};

