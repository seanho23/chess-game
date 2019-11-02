package com.seanhoapps.chessgame;

import java.util.Scanner;

public class ChessGame {
	public static void main(String[] args) {
//		Scanner input = new Scanner(System.in);
//		
//		System.out.print("Player 1 (White) name: ");
//		String player1Name = input.nextLine();
//		Player player1 = new Player(player1Name);
//		
//		System.out.print("Player 2 (Black) name: ");
//		String player2Name = input.nextLine();
//		Player player2 = new Player(player2Name);

		Player player1 = new Player("sean");
		Player player2 = new Player("mom");
		Game chess = new Game(player1, player2);
	}
}
