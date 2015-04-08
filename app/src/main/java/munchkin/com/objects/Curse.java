package munchkin.com.objects;

/**
 * Created by darkbobo on 3/3/15.
 */
public class Curse extends Door {
    STATUS_EFFECT effect;
    String effectDescription;
    int effectQuantity;
    public Curse(){}
    public Curse(String cardName, CARD_TYPE cardType, int cardImage, STATUS_EFFECT effect, String effectDescription, int effectQuantity){
        super(cardName, cardType, cardImage);
        setEffect(effect);
        setEffectDescription(effectDescription);
        setEffectQuantity(effectQuantity);
    }
    public STATUS_EFFECT getEffect(){ return effect; }
    public void setEffect(STATUS_EFFECT effect){ this.effect = effect; }
    public String getEffectDescription(){ return effectDescription;}
    public void setEffectDescription(String effectDescription){ this.effectDescription = effectDescription; }
    public int getEffectQuantity(){ return effectQuantity; }
    public void setEffectQuantity(int effectQuantity){ this.effectQuantity = effectQuantity; }
}
