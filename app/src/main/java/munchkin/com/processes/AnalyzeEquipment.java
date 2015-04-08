package munchkin.com.processes;

import java.util.ArrayList;

import munchkin.com.munchkin.Main;
import munchkin.com.objects.CARD_TYPE;
import munchkin.com.objects.Card;
import munchkin.com.objects.Gear;
import munchkin.com.objects.GearPositions;

/**
 * Created by darkbobo on 3/6/15.
 */
public class AnalyzeEquipment {
    int playerNumber;
    public AnalyzeEquipment(int playerNumber){
        this.playerNumber = playerNumber;
    }
    // returns arrraylist of armor should equip
    public void compareCards(){
        if(Main.game.getPlayer(playerNumber).getInventory().size() == 0) return;
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.addAll(Main.game.getPlayer(playerNumber).getInventory());
        for(Card card : cards){
            // if gear is in inventory and slot is not equipped, equip gear
            if(card.getCardType() == CARD_TYPE.GEAR){
                compareGear((Gear) card);
            }else if(card.getCardType() == CARD_TYPE.RACE){
                // search gear for gear that would be good for race
            }else if(card.getCardType() == CARD_TYPE.CLASS){
                // search gear for gear that would be good for class
            }
        }
    }
    // check hands in comparison to other hand combinations
    // compare equipped gear's values to gear in inventory's values
    // return suggested inventory
    private void compareGear(Gear newGear){
        if(Main.game.getPlayer(playerNumber).getGear() == null){
            Main.game.getPlayer(playerNumber).forceEquipGear(newGear);
        }
        for(Card curGear : Main.game.getPlayer(playerNumber).getGear()){
            if(((Gear)curGear).getGearPosition() == newGear.getGearPosition()){
                if(newGear.getGearPosition() != GearPositions.HAND) {
                    if (((Gear) curGear).getBonusAmount() < newGear.getBonusAmount()) {
                        Main.game.getPlayer(playerNumber).forceEquipGear(newGear);
                    }else return;
                }else{
                    if(Main.game.getPlayer(playerNumber).getNumHands() == 1 && newGear.getNumHands() == 1){
                        Main.game.getPlayer(playerNumber).forceEquipGear(newGear);
                    }else if(Main.game.getPlayer(playerNumber).getNumHands() == 2 && newGear.getNumHands() == 1 && ((Gear)curGear).getNumHands() == 1){
                        if(((Gear)curGear).getBonusAmount() < newGear.getBonusAmount()){
                            Main.game.getPlayer(playerNumber).forceEquipGear(newGear);
                        }else return;
                    }
                }
                return;
            }
        }
    }
}
