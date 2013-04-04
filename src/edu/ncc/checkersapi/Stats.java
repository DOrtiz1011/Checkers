package edu.ncc.checkersapi;

import edu.ncc.checkersapi.CheckerBoard.PlayerTurn;


public class Stats {
	
	private int gamesPlayed, lightWins, darkWins;
	private int totalMoves, captures, lightCaptured, darkCaptured;
	private int jump, doubleJump, tripleJump, quadJump, megaJump;
	
	public Stats(){
		gamesPlayed = 0;
		lightWins = 0;
		darkWins = 0;
		totalMoves = 0;
		captures = 0;
		lightCaptured = 0;
		darkCaptured = 0;
		jump = 0;
		doubleJump = 0;
		tripleJump = 0;
		quadJump = 0;
		megaJump = 0;
	}

	protected void gameOver(PlayerTurn player){
		gamesPlayed++;
		if(player == PlayerTurn.LightsTurn){
			lightWins++;
		}
		else
			darkWins++;
	}
	
	protected void jump(int jumps, PlayerTurn player){
		captures += jumps;
		if(player == PlayerTurn.LightsTurn){
			darkCaptured += jumps;
		}
		else
			lightCaptured += jumps;
		if(jumps == 1)
			jump++;
		if(jumps == 2)
			doubleJump++;
		if(jumps == 3)
			tripleJump++;
		if(jumps == 4)
			quadJump++;
		if(jumps >= 5)
			megaJump++;
	}
	
	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public int getLightWins() {
		return lightWins;
	}

	public int getDarkWins() {
		return darkWins;
	}

	public int getTotalMoves() {
		return totalMoves;
	}

	public int getCaptures() {
		return captures;
	}

	public int getLightCaptured() {
		return lightCaptured;
	}

	public int getDarkCaptured() {
		return darkCaptured;
	}

	public int getJump() {
		return jump;
	}

	public int getDoubleJump() {
		return doubleJump;
	}

	public int getTripleJump() {
		return tripleJump;
	}

	public int getQuadJump() {
		return quadJump;
	}

	public int getMegaJump() {
		return megaJump;
	}


}
