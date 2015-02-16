import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This is the model of the Mancala game and is part of the MVC pattern (model).
 * 
 * @author Team SDM
 * @version for Project, CS 151, SJSU, Spring 2013
 * 
 */
public class MancalaModel {

  ArrayList<ChangeListener> listeners;

	int[] player;
	int[][] pits;
	int[] mancala;
	int NUMBER_OF_PITS = 6;
	int NUMBER_OF_PLAYERS = 2;
	int currentPlayer; // Player #1 = 0, Player #2 = 1

	int maxUndo = 3; // Max of 3 undos
	int currentUndoPlayer; // Current Player for Undo method
	int undoCount[];
	int[][] undoPit;
	int[] undoMancala;
	boolean undoTurn;
	boolean canUndo;

	/**
	 * Constructs a Mancala object
	 */
	public MancalaModel() {
		listeners = new ArrayList<ChangeListener>();
		player = new int[NUMBER_OF_PLAYERS];
		pits = new int[NUMBER_OF_PLAYERS][NUMBER_OF_PITS];
		mancala = new int[NUMBER_OF_PLAYERS];
		undoPit = new int[NUMBER_OF_PLAYERS][];
		currentPlayer = 0; // Player 1
		currentUndoPlayer = 0;
		undoTurn = false;
		canUndo = false;
		undoCount = new int[2];
		undoCount[0] = 0;
		undoCount[1] = 0;

	}

	/**
	 * (Should be called when user enters number of stones) Sets the initial
	 * pits to the number of stones entered by user.
	 * 
	 * @param numberOfStones
	 *            the number of stones initially in each pit
	 */
	public void setNumberOfStones(int numberOfStones) {
		for (int player = 0; player < NUMBER_OF_PLAYERS; player++) {
			for (int p = 0; p < NUMBER_OF_PITS; p++) {
				pits[player][p] = numberOfStones;
			}
		}

		update();
	}

	/**
	 * Attach a listener to the Model
	 * 
	 * @param c
	 *            the listener
	 */
	public void attach(ChangeListener c) {
		listeners.add(c);
	}

	/**
	 * When the data in the model is changed, update all the listeners.
	 */
	public void update() {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * Gets the pits and their corresponding stones.
	 * 
	 * @return
	 */
	public int[][] getPits() {
		return pits.clone();
	}

	/**
	 * Gets the Mancala and the number of stones in each mancala.
	 * 
	 * @return
	 */
	public int[] getMancalas() {
		return mancala.clone();
	}

	/**
	 * Gets the current player.
	 * 
	 * @return current player. 0 is Player 1 and 1 is Player 2.
	 */
	public int getPlayer() {
		return currentPlayer;
	}

	/**
	 * Undo previous player's move
	 * 
	 * @todo add undo count to move method
	 */
	public void undo() {

		System.out.println("UNDOING");
		// if player made maximum undos
		if (undoCount[currentUndoPlayer] == maxUndo)
			return;

		// resets all data back to last move
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++)
			pits[i] = undoPit[i].clone();
		mancala = undoMancala.clone();
		currentPlayer = currentUndoPlayer;
		undoCount[currentUndoPlayer]++;
		undoTurn = false;
		undoState(); // updates board
		update();
	}

	/**
	 * Clones the state of the board
	 */
	public void undoState() {
		undoMancala = mancala.clone();
		for (int i = 0; i < NUMBER_OF_PLAYERS; i++)
			undoPit[i] = pits[i].clone();
	}
	
	

	/**
	 * Make a move on the board
	 * 
	 * @param playerNumber
	 *            the player that made the move
	 * @param chosenPitNumber
	 *            the pit number that the player made the move on.
	 */
	public void move(int playerNumber, int chosenPitNumber) {
		// throws exception if player tries to move when it's not their turn
		if(playerNumber != currentPlayer) 
			throw new IllegalArgumentException("It is not your turn.");
		
		//if empty pit is chosen
		if(pits[playerNumber][chosenPitNumber]== 0)
			return;
		
		undoState();
		if(undoTurn) {
			undoCount[playerNumber] = 0;
			undoTurn = false;
		}
		else if(undoCount[playerNumber] == 0) {
			//resets the undo count of the other player
			undoCount[nextPlayer(playerNumber)] = 0;
			currentUndoPlayer = playerNumber;
		}
		
		canUndo = true;
		int moves = pits[playerNumber][chosenPitNumber];
		pits[playerNumber][chosenPitNumber] =0;
		while(moves >0){
			chosenPitNumber = moveToPit(chosenPitNumber);
			if(chosenPitNumber ==0) 
			{
				if(playerNumber == currentPlayer)
				{
					// check to see if marble was placed
					++mancala[playerNumber];
					--moves;
					if(moves<=0)
					{
						//check to see if current player gets next turn
						checkOneSideEmpty(playerNumber);
						undoTurn = true;
						update();
						return;
					}
				}
				playerNumber = nextPlayer(playerNumber);
			}
			++pits[playerNumber][chosenPitNumber];
			--moves;
		}
		lastStoneDrop(playerNumber, chosenPitNumber);
		update();
		checkOneSideEmpty(playerNumber);
		
	}
	
	/**
	 * Next player's turn
	 */
	
	public void nextTurn() {
		currentPlayer = nextPlayer(currentPlayer);
	}
	
	/**
	 * Moves to next pit
	 * @param pit a pit
	 * @return next pit
	 */
	public int moveToPit(int pit)
	{
		if (++pit >= NUMBER_OF_PITS)
			pit = 0;
		return pit;
	}
	
	/**
	 * Gets next player
	 * @param playerNumber a player number
	 * @return the next player 
	 */
	public int nextPlayer(int playerNumber)
	{
		if (++playerNumber >= NUMBER_OF_PLAYERS)
			playerNumber = 0;
		return playerNumber;
	}


	/**
	 * if checkLastStoneDrop() is true, then update player's Mancala by taking
	 * that stone and all of your opponents stones that are in the opposite pit.
	 */
	public void lastStoneDrop(int playerNumber, int endingPit) {
		if(playerNumber == currentPlayer && pits[playerNumber][endingPit] == 1){
			//find opposite side
			int nextPlayer = this.nextPlayer(currentPlayer);
			int oppositeNumMarbles = pits[nextPlayer][5- endingPit] + 1;
			
			//set both opposite pits to 0
			pits[nextPlayer][5- endingPit] = 0;
			pits[playerNumber][endingPit] = 0;
			
			
			mancala[playerNumber] += oppositeNumMarbles;
			
			
			
		}
		
		nextTurn();

	}

	/**
	 * Checks if all six pits on one side of the Mancala board are empty.
	 * 
	 * @param the
	 *            side to check for
	 * @return boolean value for whether a side is empty or not.
	 */
	public boolean checkOneSideEmpty(int side) {
		int countMarbles = 0;
		for(int i = 0; i < NUMBER_OF_PITS; i++){
			countMarbles = countMarbles + pits[side][i];
		}
		
		if(countMarbles == 0){
			endGame();
			return true;
		}
		else
			return false;
	}

	/**
	 * End the game and distribute all stones appropriately.
	 */
	public void endGame() {
		int mancalaCountPlayer1 = 0;
		int mancalaCountPlayer2 = 0;
		
		//collect remaining stones in the pit and place it in the correct mancala
		for(int i = 0; i < NUMBER_OF_PITS; i++){
			mancalaCountPlayer1 += pits[0][i];
			mancalaCountPlayer1 += pits[1][i];
			
			pits[0][i] = 0;
			pits[1][i] = 0;
			
			
		}
		
		mancala[0] += mancalaCountPlayer1;
		mancala[1] += mancalaCountPlayer2;

	}

}