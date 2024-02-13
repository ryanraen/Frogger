package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.KeyHandler;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	
	public BufferedImage up1, up2, left1, left2, down1, down2, right1, right2, death;
	public final int screenX;
	public final int screenY;
	public boolean canMoveByKeyRelease;
	public boolean canMoveByTick;
	public boolean onLog;
	public boolean onCrocodile;
	
	public boolean alive;
	
	public Log followingLog;
	public Crocodile followingCrocodile;
	
	public int skin;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 20;
		solidArea.y = 35;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 35;
		solidArea.height = 40;
		
		alive = true;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {

		worldX = gp.tileSize * 9;
		worldY = gp.tileSize * 32;
		speed = 80;
		moving = false;
		direction = "up";
		canMoveByKeyRelease = true;
		canMoveByTick = true;
		onLog = false;
	}
	
	public void getPlayerImage() {
		
		String skinPath = "player_";
		
		switch(gp.skin) {
		case 1:
			break;
		case 2:
			skinPath = "skin_1_";
			break;
		case 3:
			skinPath = "skin_2_";
			break;
		}
		
		try{
			
			// UP
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/" + skinPath + "00.png")); // idle image
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/" + skinPath + "01.png")); // JUMP IMAGE
			// LEFT
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/" + skinPath + "02.png")); // idle image
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/" + skinPath + "03.png")); // JUMP IMAGE
			// DOWN
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/" + skinPath + "04.png")); // idle image
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/" + skinPath + "05.png")); // JUMP IMAGE
			// RIGHT
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/" + skinPath + "06.png")); // idle image
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/" + skinPath + "07.png")); // JUMP IMAGE
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
//		System.out.println("PLAYER UPDATING");
		
		if(worldY <= 6*gp.tileSize) {
			gp.dChecker.winGame();
		}
		
		// FOLLOW LOG
		if(onLog) {
			if(followingLog.direction.equals("left")) {
				collisionOn = false;
				gp.cChecker.checkTile(this);
				
				if(collisionOn == false) {
					worldX-=followingLog.speed*2;
				}
			}
			else if(followingLog.direction.equals("right")) {
				if(collisionOn == false) {
					worldX+=followingLog.speed*2;
				}
			}
		}
		
		if(onCrocodile) {
			if(followingCrocodile.direction.equals("left")) {
				collisionOn = false;
				gp.cChecker.checkTile(this);
				
				if(collisionOn == false) {
					worldX-=followingCrocodile.speed*2;
				}
			}
			else if(followingCrocodile.direction.equals("right")) {
				if(collisionOn == false) {
					worldX+=followingCrocodile.speed*2;
				}
			}
		}
		
		if(!onLog && !onCrocodile){
			gp.dChecker.checkWater(this);
		}
		
		// MOVE USING KEYS
		if(keyH.upPressed || keyH.downPressed ||
				keyH.leftPressed || keyH.rightPressed) {
			
			System.out.println("KEY PRESSED");
			
			moving = true;
			
			if(keyH.upPressed == true) {
				direction = "up";
			}
			else if(keyH.downPressed == true) {
				direction = "down";
			}
			else if(keyH.leftPressed == true) {
				direction = "left";
			}
			else if(keyH.rightPressed == true) {
				direction = "right";
			}
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			if(collisionOn == false && canMoveByTick && canMoveByKeyRelease) {
				switch(direction) {
				case "up": worldY -= speed;
				System.out.println("AAAAAA");
					break;
				case "down": worldY += speed;
					break;
				case "left": worldX -= speed;
					break;
				case "right": worldX += speed;
					break;
				}
				canMoveByKeyRelease = false;
				canMoveByTick = false;
			}
		}
		else {
			moving = false;
			canMoveByKeyRelease = true;
		}
		
		gp.dChecker.checkCar(this);
		gp.dChecker.checkOutOfMap(this);
		
	}
	
	public void getDeathImage() {
		try {
			death = ImageIO.read(getClass().getResourceAsStream("/player/dead.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		
//		System.out.println("DRAWING PLAYER");
		
		BufferedImage image = null;
		
		if(!alive) {
			getDeathImage();
			g2.drawImage(death, screenX, screenY, gp.tileSize, gp.tileSize, null);
			return;
		}
		
		if(moving) {
			switch(direction) {
			
			case "up":
				image = up2;
				break;
			case "left":
				image = left2;
				break;
			case "down":
				image = down2;
				break;
			case "right":
				image = right2;
				break;
				
			}
		}
		else {
			switch(direction) {
			
			case "up":
				image = up1;
				break;
			case "left":
				image = left1;
				break;
			case "down":
				image = down1;
				break;
			case "right":
				image = right1;
				break;
				
			}
		}
		
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
	
}
