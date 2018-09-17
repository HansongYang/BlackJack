package comp3004;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	
	public void console() {
		deck = new Deck();
		deck.shuffle();
		player = new Hand();
		dealer = new Hand();
		
		player.add(deck.next());
		System.out.println("Player receives " + player.getLastCard().getRankName() + " of " + player.getLastCard().getSuitName() + ".");
		player.add(deck.next());
		System.out.println("Player receives " + player.getLastCard().getRankName() + " of " + player.getLastCard().getSuitName() + ".\n");
		dealer.add(deck.next());
		System.out.println("Dealer receives " + dealer.getLastCard().getRankName() + " of " + dealer.getLastCard().getSuitName() + ".");
		dealer.add(deck.next());
		System.out.println("Dealer has one card face down.");
		System.out.println("Player's current score: " + player.value() +". \n");
		if(dealer.hasBlackJack()) {
			System.out.println("Dealer has blackjack. Dealer's score is " + dealer.value() + ".");
			return;
		} else if(player.hasBlackJack() && !dealer.hasBlackJack()) {
			System.out.println("The player has blackjack. Player's score is " + player.value() + ". Dealer's score is " + dealer.value() + ".");
			return;
		}
		game();
	}
	
	public void file() {
		player = new Hand();
		dealer = new Hand();
		System.out.println("Please input a file's path.");
		choose = sc.nextLine();
	
		BufferedReader br = null;
		String line = "";
		try {                                                                                                                                                                                                                                                                             
			br = new BufferedReader(new FileReader(choose));                                                                                                                                                                                                                                                                                                                                                                                                                                                             
			if((line = br.readLine()) != null) {                                                                                                                                                                                                                                        
				System.out.println("File content: " + line);
			}                                                                                                                                                                                                                                                                               
		} catch(FileNotFoundException e) {                                                                                                                                                                                                                                                
			 System.err.println("File not found: " + e.getMessage());  
			 return;
		} catch(IOException e) {                                                                                                                                                                                                                                                          
			 System.err.println("Caught IOException: " + e.getMessage()); 
			 return;
		}finally {                                                                                                                                                                                                                                                                        
			if (null != br) { 
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
					return;
				} 
			}                                                                                                                                                                                                                                                 
		}
	//	game2(line);
	}
	
	public void PlayerHit() {
		if(player.splitted) {
			if(second) {
				player.add2(deck.next());
				System.out.println("Player receives " + player.getLastCard2().getRankName() + " of " + player.getLastCard2().getSuitName() + ".");
				System.out.println("Player's second stack current score: " + player.value2() + ".");
				if(player.isBusted2()) {
					System.out.println("Player's second stack busted.  Player's second stack score is " + player.value2() + ".");
				}
			}else {
				player.add(deck.next());
				System.out.println("Player receives " + player.getLastCard().getRankName() + " of " + player.getLastCard().getSuitName() + ".");
				System.out.println("Player's first stack current score: " + player.value() + ".");
				if(player.isBusted()) {
					System.out.println("Player's first stack busted. Player's first stack score is " + player.value() + ".");
				}
			}
		}else {
			player.add(deck.next());
			System.out.println("Player receives " + player.getLastCard().getRankName() + " of " + player.getLastCard().getSuitName() + ".");
			System.out.println("Player's current score: " + player.value() + ".");
			if(player.isBusted()) {
				System.out.println("Player busted. Dealer Wins! Dealer's score is " + dealer.value() + ". Player's score is " + player.value() + ".");
				stop = true;
			}
		}
	}
	
	public void DealerHit() {
		if(player.isBusted() && player.isBusted2()) {
			System.out.println("Dealer wins. Dealer's score is " + dealer.value() + ". Player's first stack score is " + player.value() + ". Player's second stack score is " + player.value2() + ".");
			return;
		}
		System.out.println("Dealer has " + dealer.getFirstCard().getRankName() + " of " + dealer.getFirstCard().getSuitName() + " and "+ dealer.getLastCard().getRankName() + " of " + dealer.getLastCard().getSuitName() + ".");
		while(true) {
			if(dealer.value() <= 16 || (dealer.value() == 17 && (dealer.getFirstCard().getRankName().equals("Ace") || dealer.getLastCard().getRankName().equals("Ace")))) {
				System.out.println("Dealer choose to Hit!");
				dealer.add(deck.next());
				System.out.println("Dealer receives " + dealer.getLastCard().getRankName() + " of " + dealer.getLastCard().getSuitName() + ".");
				System.out.println("Dealer's current score: " + dealer.value() + ".");
				if(dealer.isBusted()) {
					System.out.println("Dealer busted. Player wins! Player's score is " + player.value() + ". Dealer's score is " + dealer.value() + ".");
					stop = true;
					return;
				}
			} else {
				System.out.println("Dealer choose to stand!");
				if(player.splitted) {
					if(player.isBusted() && dealer.value() >= player.value2()) {
						System.out.println("Dealer wins. Dealer's score is " + dealer.value() + ". Player's first stack score is " + player.value() + ". Player's second stack score is " + player.value2() + ".");
					}else if(player.isBusted2() && dealer.value() >= player.value()) {
						System.out.println("Dealer wins. Dealer's score is " + dealer.value() + ". Player's first stack score is " + player.value() + ". Player's second stack score is " + player.value2() + ".");
					}else if(dealer.value() >= player.value() && dealer.value() >= player.value2()) {
						System.out.println("Dealer wins. Dealer's score is " + dealer.value() + ". Player's first stack score is " + player.value() + ". Player's second stack score is " + player.value2() + ".");
					}else if(player.isBusted() && player.value2() > dealer.value()) {
						System.out.println("Player's second stack wins. Player's second stack score is " + player.value2() + ". Dealer's score is " + dealer.value() + ".");
					}else if(player.isBusted2() && player.value() > dealer.value()) {
						System.out.println("Player's first stack wins. Player's first stack score is " + player.value() + ". Dealer's score is " + dealer.value() + ".");
					}else {
						if(player.value() > player.value2()) {
							System.out.println("Player's first stack wins. Player's first stack score is " + player.value() + ". Dealer's score is " + dealer.value() + ".");
						}else {
							System.out.println("Player's second stack wins. Player's second stack score is " + player.value2() + ". Dealer's score is " + dealer.value() + ".");
						}
					}
					stop = true;
					return;
				}else {
					if(dealer.value() >= player.value()) {
						System.out.println("Dealer wins. Dealer's score is " + dealer.value() + ". Player's score is " + player.value() + ".");
					}else {
						System.out.println("Player wins. Player's score is " + player.value() + ". Dealer's score is " + dealer.value() + ".");
					}
					stop = true;
					return;
				}
			}
		}
	}
	
	public void game() {
		while(!stop) {
			if(player.split()) {
				System.out.println("Please choose to Hit (H) or Stand (S) or Split (D).");
			}else {
				System.out.println("Please choose to Hit (H) or Stand (S).");
			}
			choose = sc.nextLine();
			if(choose.equals("H") || choose.equals("h") ) {
				PlayerHit();
			}else if (choose.equals("S") || choose.equals("s") ) {
				if(dealer.value() <= 17 && dealer.split()) {
					split2();
				}else {
					DealerHit();
				}
			}else if (choose.equals("D") || choose.equals("d")) {
				split();
			}else {
				System.out.println("Wrong input. Please input again.");
			}
		}
	}
	
	public void split() {
		System.out.println("Player choose to split.");
		player.add(deck.next());
		System.out.println("Player receives " + player.getLastCard().getRankName() + " of " + player.getLastCard().getSuitName() + " for the first stack.");
		System.out.println("First Stack: Player's current score: " + player.value() + ". \n");
		
		while(!stop) {
			if(!player.isBusted()) {
				System.out.println("Please choose to Hit (H) or Stand (S) for the first stack.");
				choose = sc.nextLine();
			}
			if((choose.equals("H") || choose.equals("h")) && !player.isBusted()) {
				PlayerHit();
			} else if (choose.equals("S") || choose.equals("s") || player.isBusted() || player.hasBlackJack()) {
				player.add2(deck.next());
				System.out.println("Player receives " + player.getLastCard2().getRankName() + " of " + player.getLastCard2().getSuitName() + " for the second stack.");
				System.out.println("Second Stack: Player's current score: " + player.value2() + ". \n");				
				
				while(!stop) {
					if(!player.isBusted2()) {
						System.out.println("Please choose to Hit (H) or Stand (S) for the second stack.");
						choose = sc.nextLine();
					}
					if((choose.equals("H") || choose.equals("h")) && !player.isBusted2()) {
						second = true;
						PlayerHit();
						second = false;
					}else if (choose.equals("S") || choose.equals("s") || player.isBusted2()) {
						DealerHit();
					}else {
						System.out.println("Wrong input. Please input again.");
					}
				}
			}else {
				System.out.println("Wrong input. Please input again.");
			}
		}
	}
	
	public void split2() {
		System.out.println("Dealer choose to split.");
		dealer.add(deck.next());
		System.out.println("Dealer receives " + dealer.getLastCard().getRankName() + " of " + dealer.getLastCard().getSuitName() + " for the first stack.");
		System.out.println("First Stack: Dealer's current score: " + dealer.value() + ". \n");
		
		while(true) {
			if(dealer.value() <= 16 || (dealer.value() == 17 && (dealer.getFirstCard().getRankName().equals("Ace") || dealer.getLastCard().getRankName().equals("Ace")))) {
				System.out.println("Dealer choose to Hit for the first stack!");
				dealer.add(deck.next());
				System.out.println("Dealer receives " + dealer.getLastCard().getRankName() + " of " + dealer.getLastCard().getSuitName() + " for the first stack.");
				System.out.println("Dealer's first stack current score: " + dealer.value() + ".");
				if(dealer.isBusted()) {
					System.out.println("Dealer's first stack busted.");
				}
			} else if (dealer.value2() <= 16 || (dealer.value2() == 17 && (dealer.getFirstCard2().getRankName().equals("Ace") || dealer.getLastCard2().getRankName().equals("Ace")))) {
				if(dealer.count2 == 1){
					dealer.add2(deck.next());
					System.out.println("Dealer receives " + dealer.getLastCard2().getRankName() + " of " + dealer.getLastCard2().getSuitName() + " for the second stack.");
					System.out.println("Second Stack: Dealer's current score: " + dealer.value2() + ". \n");
				}else{
					System.out.println("Dealer choose to Hit for the second stack!");
					dealer.add2(deck.next());
					System.out.println("Dealer receives " + dealer.getLastCard2().getRankName() + " of " + dealer.getLastCard2().getSuitName() + " for the second stack.");
					System.out.println("Dealer's second stack current score: " + dealer.value2() + ".");
				}
				if(dealer.isBusted2()) {
					System.out.println("Dealer's second stack busted.");
				}
			} else {
				if(dealer.isBusted() && dealer.isBusted2()) {
					System.out.println("Player wins. Player's score is " + player.value() + ". Dealer's first stack score is " + dealer.value() + ". Dealer's second stack score is " + dealer.value2() + ".");
				}else if(dealer.isBusted() && player.value() > dealer.value2()) {
					System.out.println("Player wins. Player's score is " + player.value() + ". Dealer's first stack score is " + dealer.value() + ". Dealer's second stack score is " + dealer.value2() + ".");
				}else if(dealer.isBusted2() && player.value() >= dealer.value()) {
					System.out.println("Player wins. Player's score is " + player.value() + ". Dealer's first stack score is " + dealer.value() + ". Dealer's second stack score is " + dealer.value2() + ".");
				}else if(player.value() >= dealer.value() && player.value() >= dealer.value2()) {
					System.out.println("Player wins. Player's score is " + player.value() + ". Dealer's first stack score is " + dealer.value() + ". Dealer's second stack score is " + dealer.value2() + ".");
				}else if(dealer.isBusted() && dealer.value2() >= player.value()) {
					System.out.println("Dealer's second stack wins. Dealer's second stack score is " + dealer.value2() + ". Player's score is " + player.value() + ".");
				}else if(dealer.isBusted2() && dealer.value() >= player.value()) {
					System.out.println("Dealer's first stack wins. Dealer's first stack score is " + dealer.value() + ". Player's score is " + player.value() + ".");
				}else {
					if(dealer.value() > dealer.value2()) {
						System.out.println("Dealer's first stack wins. Dealer's first stack score is " + dealer.value() + ". Player's score is " + player.value() + ".");
					}else {
						System.out.println("Dealer's second stack wins. Dealer's second stack score is " + dealer.value2() + ". Player's score is " + player.value() + ".");
					}
				}
				stop = true;
				break;
			}
		}
	}
	
	public boolean input(String choose) {
		if(choose.equals("c") || choose.equals("C") ) {
			console();
		}
		else if(choose.equals("f") || choose.equals("F") ) {
			file();
		}
		else {
			System.out.println("Wrong input, please input again.");
			start();
			return false;
		}
		return true;
	}
	
	public void start() {
		System.out.println("Please select console (c) input or file (f) input.");
		String choose = sc.nextLine();
		input(choose);
	}
	
	public static void main(String[] args){
		new BlackJack();
	}
}
