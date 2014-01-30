package de.cosh.anothermanager.SwapGame;

public class RespawnRequest {
	int[] respawnsRequestedInCol;
	
	public RespawnRequest() {
		respawnsRequestedInCol = new int[Board.MAX_SIZE_X];
		clear();
	}
	
	public void clear() {
		for( int x = 0; x < Board.MAX_SIZE_X; x++ ) {
			respawnsRequestedInCol[x] = 0;
		}
	}
	
	public void addOne(int col) {
		respawnsRequestedInCol[col]++;
	}
	
	public int howManyForCol(int col) {
		return respawnsRequestedInCol[col];
	}
}
