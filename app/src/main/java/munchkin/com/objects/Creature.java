package munchkin.com.objects;

public class Creature extends Door{
	int level;
    String creatureType;
    BAD_STUFF badStuff;
    int rewardTreasure;
    int rewardLevel;
    String hatedClass;
    int hatedValue;
    String hatedEffect;
    public Creature(){}
    public Creature(String cardName, int cardImage, int level, BAD_STUFF badStuff, int rewardTreasure,
                    int rewardLevel, String hatedClass, int hatedValue, String hatedEffect){
        super(cardName, CARD_TYPE.CREATURE, cardImage);
        setLevel(level);
        setBadStuff(badStuff);
        setRewardTreasure(rewardTreasure);
        setRewardLevel(rewardLevel);
        setHatedClass(hatedClass);
        setHatedValue(hatedValue);
        setHatedEffect(hatedEffect);
    }
    public int getLevel(){ return level; }
    public void setLevel(int level){ this.level = level; }
    public String getCreatureType(){ return creatureType; }
    public void setCreatureType(String classType){ this.creatureType = classType; }
    public BAD_STUFF getBadStuff(){ return badStuff; }
    public void setBadStuff(BAD_STUFF badStuff){ this.badStuff = badStuff; }
    public int getRewardTreasure(){ return rewardTreasure; }
    public void setRewardTreasure(int rewardTreasure){ this.rewardTreasure = rewardTreasure; }
    public int getRewardLevel() { return rewardLevel; }
    public void setRewardLevel(int rewardLevel) { this.rewardLevel = rewardLevel; }
    public String getHatedClass() { return hatedClass; }
    public void setHatedClass(String hatedClass) { this.hatedClass = hatedClass; }
    public String getHatedEffect() {return hatedEffect; }
    public void setHatedEffect(String hatedEffect) { this.hatedEffect = hatedEffect; }
    public int getHatedValue() { return hatedValue; }
    public void setHatedValue(int hatedValue) { this.hatedValue = hatedValue; }
}
