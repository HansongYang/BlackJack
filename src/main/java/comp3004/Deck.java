package comp3004;

import java.awt.*;
import java.util.*;

public class Deck{
	private Card[] cards= new Card[52];
	private int number = 0;
	
	public Deck() {
		int k = 0;
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 13; j++){
				Card a = new Card(i,j);
				cards[k] = a;
				k++;
			}
		}
	}
	
	public Card next(){
		if(number < 52){
			number++;
			return cards[number-1];
		}
		else{
			return null;
		}
	}
	
	public void shuffle(){
		number = 0;
		int count=0;
		while (count < 1000){
			Random rand = new Random();
			int x= rand.nextInt(52);
			int y= rand.nextInt(52);
			Card stored = cards[x];
			cards[x] = cards[y];
			cards[y] = stored;
			count++;
		}
	}
}
