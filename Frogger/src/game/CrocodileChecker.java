package game;

import entity.Crocodile;

public class CrocodileChecker {
	
	GamePanel gp;
	public boolean onCrocodile;
	public Crocodile crocodile;
	
	public CrocodileChecker(GamePanel gp) {
		this.gp = gp;
		onCrocodile = false;
		crocodile = null;
	}
	
	public void checkPosition() {
		onCrocodile = false;
		for(int i = 0; i < Crocodile.crocodiles.size(); i++) {
			
			if(gp.player.worldX+gp.tileSize/2 > Crocodile.crocodiles.get(i).worldX && 
					gp.player.worldX+gp.tileSize/2 < Crocodile.crocodiles.get(i).worldX+(Crocodile.crocodiles.get(i).length)*gp.tileSize &&
					gp.player.worldY+gp.tileSize/2 < Crocodile.crocodiles.get(i).worldY+gp.tileSize &&
					gp.player.worldY+gp.tileSize/2 > Crocodile.crocodiles.get(i).worldY) {
				onCrocodile = true;
				crocodile = Crocodile.crocodiles.get(i);
				break;
			}

		}
	}
	
	public void update() {
		
		checkPosition();
		if(onCrocodile) {
			// MOVE PLAYER WITH CROCODILE
			gp.player.onCrocodile = true;
			gp.player.followingCrocodile = crocodile;
		}
		else {
			gp.player.onCrocodile = false;
			gp.player.followingCrocodile = null;
		}
	}
}
