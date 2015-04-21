package munchkin.com.processes;

import android.content.Context;

/**
 * Created by darkbobo on 3/6/15.
 */
public class CPProcess implements Runnable {
    Context context;
    int playerNumber;
    ProcessPhase phase;
    int difficulty;
    public CPProcess(Context context, int playerNumber, ProcessPhase phase, int difficulty){
        this.context = context;
        this.playerNumber = playerNumber;
        this.phase = phase;
        this.difficulty = difficulty;
    }
    @Override
    public void run() {
        switch (phase){
            case EQUIP_GEAR:
                turnEquipGear();
                break;
            case RESPOND_COMBAT:

                break;
            case FINISH_TURN:
				turnEquipGear();
				applyCharity();
                break;
        }
    }
    public void turnEquipGear(){
        AnalyzeEquipment analysis = new AnalyzeEquipment(playerNumber);
        analysis.compareCards();
    }
	public void applyCharity(){
		Charity charity = new Charity(playerNumber);
		charity.executeCharity();
	}
}
