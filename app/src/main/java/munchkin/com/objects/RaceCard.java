package munchkin.com.objects;

/**
 * Created by darkbobo on 3/3/15.
 */
public class RaceCard extends Door{
    String effect1;
    String effect2;
    public RaceCard(){}
    public RaceCard(String cardName, int cardImage){
        super(cardName, CARD_TYPE.RACE, cardImage);
    }

    // add class: Effect
    public String getEffect1(){ return effect1; }
    public void setEffect1(String effect1){ this.effect1 = effect1; }
    public String getEffect2(){ return effect2; }
    public void setEffect2(String effect2){ this.effect2 = effect2; }
}
