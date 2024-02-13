package game;

import java.awt.image.BufferedImage;

import entity.Car;
import entity.Player;

public class DeathChecker {
	
	BufferedImage death;
	GamePanel gp;
	
	public DeathChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkWater(Player Player) {
		
		int PlayerWorldX = Player.worldX;
		int PlayerWorldY = Player.worldY;
		
		int currentTile = gp.tileM.mapTileNum[PlayerWorldX/gp.tileSize][PlayerWorldY/gp.tileSize];
		
		// IF CURRENT TILE IS WATER
		if(currentTile == 4) {
			System.out.println("DEAD: WATER");
			endGame();
		}
		
	}
	
	public void checkOutOfMap(Player Player) {
		
		if(Player.worldX <= 0 || Player.worldX+gp.tileSize > gp.maxWorldCol*gp.tileSize || 
				Player.worldY+gp.tileSize > gp.maxWorldRow*gp.tileSize+gp.tileSize || Player.worldY < -gp.tileSize) {
			System.out.println("DEAD: OUT OF THE MAP");
			endGame();
			
		}
	}
	
	public void checkCar(Player Player) {
		for(int i = 0; i < Car.cars.size(); i++) {
			
			if(gp.player.worldX+gp.tileSize/2 > Car.cars.get(i).worldX && 
					gp.player.worldX+gp.tileSize/2 < Car.cars.get(i).worldX+gp.tileSize &&
					gp.player.worldY+gp.tileSize/2 < Car.cars.get(i).worldY+gp.tileSize &&
					gp.player.worldY+gp.tileSize/2 > Car.cars.get(i).worldY) {
				System.out.println("DEAD: CAR ACCIDENT");
				endGame();
			}
			
//			Car car = Car.cars.get(i);
//			if((gp.player.worldY < car.worldY+gp.tileSize && gp.player.worldY+gp.tileSize > car.worldY+gp.tileSize &&
//					((gp.player.worldX < car.worldX+gp.tileSize && gp.player.worldX+gp.tileSize > car.worldX+gp.tileSize) || 
//					 (gp.player.worldX+gp.tileSize > car.worldX && gp.player.worldX < car.worldX))) || 
//			   (gp.player.worldY+gp.tileSize > car.worldY && gp.player.worldY < car.worldY &&
//					   ((gp.player.worldX < car.worldX+gp.tileSize && gp.player.worldX+gp.tileSize > car.worldX+gp.tileSize) || 
//								 (gp.player.worldX+gp.tileSize > car.worldX && gp.player.worldX < car.worldX)))) {
//				
//				System.out.println("DEAD: CAR ACCIDENT");
//				endGame();
//			}

		}
	}
	
	public void endGame() {
		
		gp.player.alive = false;
		gp.ui.gameLost = true;
	}
	
	public void winGame() {
		
		gp.ui.gameWon = true;
	}

}
