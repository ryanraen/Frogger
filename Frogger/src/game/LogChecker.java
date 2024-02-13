package game;

import entity.Log;

public class LogChecker {
	
	GamePanel gp;
	public boolean onLog;
	public Log log;
	
	public LogChecker(GamePanel gp) {
		this.gp = gp;
		onLog = false;
		log = null;
	}
	
	public void checkPosition() {
		onLog = false;
		for(int i = 0; i < Log.logs.size(); i++) {
			
			if(gp.player.worldX+gp.tileSize/2 > Log.logs.get(i).worldX && 
					gp.player.worldX+gp.tileSize/2 < Log.logs.get(i).worldX+(Log.logs.get(i).length)*gp.tileSize &&
					gp.player.worldY+gp.tileSize/2 < Log.logs.get(i).worldY+gp.tileSize &&
					gp.player.worldY+gp.tileSize/2 > Log.logs.get(i).worldY) {
				onLog = true;
				log = Log.logs.get(i);
				break;
			}

		}
	}
	
	public void update() {
		
		checkPosition();
		if(onLog) {
			// MOVE PLAYER WITH LOG
			gp.player.onLog = true;
			gp.player.followingLog = log;
		}
		else {
			gp.player.onLog = false;
			gp.player.followingLog = null;
		}
	}

}
