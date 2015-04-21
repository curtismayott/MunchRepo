package munchkin.com.processes;

import java.util.ArrayList;

import munchkin.com.munchkin.Main;
import munchkin.com.objects.Card;

/**
 * Created by darkbobo on 4/19/15.
 */
public class Charity {
	int playerNumber;
	public Charity(int playerNumber){
		this.playerNumber = playerNumber;
	}
	public void executeCharity(){
		if(Main.game.getPlayer(playerNumber).getInventory().size() > Main.game.getPlayer(playerNumber).getHandCapacity()){
			return;
		}else{
			int handCount = Main.game.getPlayer(playerNumber).getInventory().size();
			int handCapacity = Main.game.getPlayer(playerNumber).getHandCapacity();
			ArrayList<Card> tradeCards = new ArrayList<Card>();
			// get class/race cards can't use
			if(Main.game.getPlayer(playerNumber).getClassRaceCards().size() > 0){
				for(int i = Main.game.getPlayer(playerNumber).getClassRaceCards().size()-1; i >= 0; i--){
					if(handCount > handCapacity){
						if(Main.game.isLowestLevelPlayer(playerNumber)) {
							Main.game.discardCard(playerNumber, Main.game.getPlayer(playerNumber).getClassRaceCards().get(i));
						}else{
							tradeCards.add(Main.game.getPlayer(playerNumber).getClassRaceCards().get(i));
						}
						handCount--;
					}
				}
			}
			/*
			TODO: discard gear can't use
			*/

			if(tradeCards.size() > 0) {
				Main.game.tradeCard(playerNumber, Main.game.getLowestLevelPlayer(), tradeCards);
			}
		}
	}
}
