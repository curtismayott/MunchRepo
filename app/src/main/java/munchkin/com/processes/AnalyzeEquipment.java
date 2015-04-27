package munchkin.com.processes;

import android.util.Log;

import java.util.ArrayList;

import munchkin.com.munchkin.Main;
import munchkin.com.objects.CARD_TYPE;
import munchkin.com.objects.Card;
import munchkin.com.objects.ClassCard;
import munchkin.com.objects.ClassEnum;
import munchkin.com.objects.Gear;
import munchkin.com.objects.GearPositions;
import munchkin.com.objects.Race;
import munchkin.com.objects.RaceCard;

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
        if(Main.game.getPlayer(playerNumber).getInventory().size() == 0){
			return;
		}
		ArrayList<Card> cards = Main.game.getPlayer(playerNumber).getInventory();
        for(int i = 0; i < cards.size(); i++){
            // if gear is in inventory and slot is not equipped, equip gear
            if(cards.get(i).getCardType() == CARD_TYPE.GEAR){
                compareGear((Gear) cards.get(i));
            }else if(cards.get(i).getCardType() == CARD_TYPE.RACE){
                // search gear for gear that would be good for race
				compareRace((RaceCard)cards.get(i));
            }else if(cards.get(i).getCardType() == CARD_TYPE.CLASS){
                // search gear for gear that would be good for class
				compareClass((ClassCard)cards.get(i));
            }
        }
    }
    // check hands in comparison to other hand combinations
    // compare equipped gear's values to gear in inventory's values
    // return suggested inventory
    private void compareGear(Gear newGear){
        if((Main.game.getPlayer(playerNumber).getGear()).size() == 0){
            Main.game.getPlayer(playerNumber).forceEquipGear(newGear);
        }
        for(Card curGear : Main.game.getPlayer(playerNumber).getGear()){
            if(((Gear)curGear).getGearPosition() == newGear.getGearPosition()){
                if(newGear.getGearPosition() != GearPositions.HAND) {
                    if (((Gear) curGear).getBonusAmount() < newGear.getBonusAmount()) {
                        Main.game.getPlayer(playerNumber).forceEquipGear(newGear);
						Main.game.getPlayer(playerNumber).unequipGear(curGear);
                    }else return;
                }else{
                    if(Main.game.getPlayer(playerNumber).getNumHands() == 1 && newGear.getNumHands() == 1){
                        Main.game.getPlayer(playerNumber).forceEquipGear(newGear);
                    }else if(Main.game.getPlayer(playerNumber).getNumHands() == 2){
                        if(((Gear)curGear).getBonusAmount() < newGear.getBonusAmount()){
                            Main.game.getPlayer(playerNumber).forceEquipGear(newGear);
							Main.game.getPlayer(playerNumber).unequipGear(curGear);
                        }else return;
                    }else if(Main.game.getPlayer(playerNumber).getNumHands() == 1){
						if(newGear.getNumHands() == 1){
							Main.game.getPlayer(playerNumber).forceEquipGear(newGear);
							Main.game.getPlayer(playerNumber).unequipGear(curGear);
						}else if(newGear.getNumHands() == 2){
							if(((Gear) curGear).getBonusAmount() < newGear.getBonusAmount()){
								Main.game.getPlayer(playerNumber).forceEquipGear(newGear);
								Main.game.getPlayer(playerNumber).unequipGear(curGear);
							}
						}
					}
                }
                return;
            }
        }
    }
	private void compareRace(RaceCard card){
		RaceCard raceCard = (RaceCard)(Main.game.getPlayer(playerNumber).getRaceCard());
		if(raceCard == null){
			Main.game.getPlayer(playerNumber).forceEquipGear(card);
		}else{
			if(raceCard.getRace() != card.getRace()){
				if(raceCard.getRace() == Race.ELF){
					// do nothing
					return;
				}else if(raceCard.getRace() == Race.DWARF){
					if(card.getRace() == Race.ELF){
						Main.game.getPlayer(playerNumber).forceEquipGear(card);
					}else if(card.getRace() == Race.HAFLING){
						Main.game.getPlayer(playerNumber).forceEquipGear(card);
					}
				}else if(raceCard.getRace() == Race.HAFLING){
					if(card.getRace() == Race.DWARF){
						// do nothing
						return;
					}else if(card.getRace() == Race.ELF){
						Main.game.getPlayer(playerNumber).forceEquipGear(card);
					}
				}
			}else{
				// do nothing
				return;
			}
		}
	}
	private void compareClass(ClassCard card){
		// TODO: evaluate gear for each class (added up)
		ClassCard classCard = (ClassCard)(Main.game.getPlayer(playerNumber).getClassCard());
		if(classCard == null){
			Main.game.getPlayer(playerNumber).forceEquipGear(card);
		}else{
			// player has a class card equipped
			if(card.getClassEnum() != classCard.getClassEnum()){
				// class is not the class the player has equipped
				if(card.getClassEnum() == ClassEnum.WIZARD){
					Main.game.getPlayer(playerNumber).forceEquipGear(card);
				}else if(card.getClassEnum() == ClassEnum.CLERIC){
					if(classCard.getClassEnum() == ClassEnum.THIEF){
						// player's class is a thief
						return;
					}else if(classCard.getClassEnum() == ClassEnum.WARRIOR){
						// player's class is a warrior
						Main.game.getPlayer(playerNumber).forceEquipGear(card);
					}
				}else if(card.getClassEnum() == ClassEnum.THIEF){
					if(classCard.getClassEnum() == ClassEnum.CLERIC){
						// player's class is a cleric
						Main.game.getPlayer(playerNumber).forceEquipGear(card);
					}else if(classCard.getClassEnum() == ClassEnum.WARRIOR){
						// player's class is a warrior
						Main.game.getPlayer(playerNumber).forceEquipGear(card);
					}
				}else if(card.getClassEnum() == ClassEnum.WARRIOR){
					if(classCard.getClassEnum() == ClassEnum.CLERIC){
						// player's class is a cleric
						return;
					}else if(classCard.getClassEnum() == ClassEnum.THIEF){
						// player's class is a thief
						return;
					}
				}
			}else{
				// do nothing
				return;
			}
		}
	}
}
