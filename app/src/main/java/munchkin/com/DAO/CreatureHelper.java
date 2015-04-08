package munchkin.com.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import munchkin.com.objects.BAD_STUFF;

/**
 * Created by darkbobo on 3/4/15.
 */
public class CreatureHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "Munchkin";
    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase db;
    public CreatureHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onCreate(getWritableDatabase());
    }
    @Override
    public void onCreate(SQLiteDatabase _db) {
        db = _db;
        db.execSQL("CREATE TABLE IF NOT EXISTS Creatures (id INTEGER PRIMARY KEY, " +
                "CardName TEXT, CardImage INTEGER, Level INTEGER, BadStuff TEXT, RewardTreasure INTEGER, " +
                "RewardLevel INTEGER, HatedClass TEXT, HatedValue INTEGER, HatedEffect TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void insert(String cardName, int cardImage, int level, String badStuff, int rewardTreasure,
                       int rewardLevel, String hatedClass, int hatedValue, String hatedEffect){
        ContentValues cv = new ContentValues();
        cv.put("CardName", cardName);
        cv.put("CardImage", cardImage);
        cv.put("Level", level);
        cv.put("BadStuff", badStuff);
        cv.put("RewardTreasure", rewardTreasure);
        cv.put("RewardLevel", rewardLevel);
        cv.put("HatedClass", hatedClass);
        cv.put("HatedValue", hatedValue);
        cv.put("HatedEffect", hatedEffect);
        getWritableDatabase().insert("Creatures", null, cv);
    }
    public Cursor getCards(){
        String sql ="SELECT * FROM Creatures;";
        return getReadableDatabase().rawQuery(sql, null);
    }
    public boolean isEmpty(){
        Cursor c = getCards();
        return c.moveToFirst();
    }
    public int getNumberCards(){
        int count = 0;
        Cursor c = getCards();
        if(!c.moveToFirst()) return count;
        c.moveToFirst();
        while(!c.isAfterLast()){
            count++;
            c.moveToNext();
        }
        return count;
    }
    public String getCardName(Cursor c){
        return c.getString(c.getColumnIndex("CardName"));
    }
    public int getCardImage(Cursor c){
        return c.getInt(c.getColumnIndex("CardImage"));
    }
    public int getLevel(Cursor c){
        return c.getInt(c.getColumnIndex("Level"));
    }
    public String getBadStuff(Cursor c){
        return c.getString(c.getColumnIndex("BadStuff"));
    }
    public int getRewardTreasure(Cursor c){
        return c.getInt(c.getColumnIndex("RewardTreasure"));
    }
    public int getRewardLevel(Cursor c){
        return c.getInt(c.getColumnIndex("RewardLevel"));
    }
    public String getHatedClass(Cursor c){
        return c.getString(c.getColumnIndex("HatedClass"));
    }
    public int getHatedValue(Cursor c){
        return c.getInt(c.getColumnIndex("HatedValue"));
    }
    public String getHatedEffect(Cursor c){
        return c.getString(c.getColumnIndex("HatedEffect"));
    }
    public void deleteDatabase(){
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS Creatures;");
    }
}
