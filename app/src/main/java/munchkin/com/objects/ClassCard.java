package munchkin.com.objects;

/**
 * Created by darkbobo on 3/3/15.
 */
public class ClassCard extends Door{
    int cardImage;
    String effect1;
    String effect2;
	ClassEnum classEnum;
    public ClassCard(){}
    public ClassCard(String cardName, int cardImage, ClassEnum classEnum){
        super(cardName, CARD_TYPE.CLASS, cardImage);
		setClassEnum(classEnum);
    }

    // add class: Effect
    public String getEffect1(){ return effect1; }
    public void setEffect1(String effect1){ this.effect1 = effect1; }
    public String getEffect2(){ return effect2; }
    public void setEffect2(String effect2){ this.effect2 = effect2; }
    @Override
    public int getCardImage() { return cardImage; }
    @Override
    public void setCardImage(int cardImage) { this.cardImage = cardImage; }
	public ClassEnum getClassEnum() { return classEnum; }
	public void setClassEnum(ClassEnum classEnum) { this.classEnum = classEnum; }
}
