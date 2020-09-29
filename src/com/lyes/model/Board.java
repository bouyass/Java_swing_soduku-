package com.lyes.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.lyes.games.Games;

public class Board {

	private int[][] board;
	private int numBoard = 0;
	
	public Board(int numBoard) {
		this.board = new int[9][9];
		this.numBoard = numBoard;
		initBoard();
	}

	public int[][] getBoard() {
		return board;
	}	
	
	// we initialize the board with values correspending to the board number giver
	public void initBoard(){
		
		int number = 0;
		int index = 0;
		Integer[] tempArray;
		List<Integer[]> boardValuesCoordinates = new ArrayList<>();
		List<Integer> boardValues = new ArrayList<>(); 

		
		// getting the game values
		for(int i = 0; i<Games.coordinates_easy.length;i++) {
			
			if(number == numBoard) {
				boardValuesCoordinates.add(Games.coordinates_easy[i]);
				boardValues.add(Games.values_easy[index]);
				index++;
			}
			if(Games.coordinates_easy[i][0] == 10) {
				number ++;
				index = i+1;
			}
			
			if(number > numBoard) {
				break;
			}
		}

		// inserting values into games matrix
		for(int j = 0; j < this.board.length; j++) {
			for( int  k = 0; k < this.board.length; k++) {
				
			    tempArray = new Integer[]{j, k};
				
			    this.board[j][k] = 10;
			    
			    for(Integer[] arr : boardValuesCoordinates) {
			    	if(Arrays.equals(arr, tempArray)) {
			    		int indexof = boardValuesCoordinates.indexOf(arr);
						this.board[j][k] = boardValues.get(indexof);
			    	}
			    }	
			}
		}
		
	}
	
	
	public void showMatrix() {
		for(int i = 0; i<9;i++) {
		for(int j = 0; j< 9; j++) {
				System.out.print(this.board[i][j]+ " | ");
			}
			System.out.println("");
		}
	}
	
}
