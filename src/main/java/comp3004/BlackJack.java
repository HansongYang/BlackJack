package comp3004;

import java.util.Scanner;

public class BlackJack {
	public Hand player;
	public Hand dealer;
	public Deck deck;
	boolean stop = false;
	boolean second = false;
	Scanner sc = new Scanner(System.in);
	String choose = "";
	String playerSuit = "",playerRank = "", dealerSuit = "", dealerRank = "";
	int suit = 0, rank = 0;
	
	public BlackJack() {
		start();
	}
	
	public void start() {
		System.out.println("Please select console (c) input or file (f) input.");
		String choose = sc.nextLine();
		if(choose.equals("c") || choose.equals("C") ) {
			console();
		}
		else if(choose.equals("f") || choose.equals("F") ) {
			file();
		}
		else {
			System.out.println("Wrong input, please input again.");
			start();
		}
	}
	
	public static void main(String[] args){
		new BlackJack();
	}
}
