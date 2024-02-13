package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import game.GamePanel;

public class Log extends Entity {
	
	GamePanel gp;
	Random gen;
	
	public BufferedImage left, middle, right;
	public int length;
	public int screenX;
	public int screenY;
	public static ArrayList<Log> logs = new ArrayList<Log>();
	
	public Log(GamePanel gp, String direction, int worldCol, int worldRow, int speed, int length) {
		
		this.gp = gp;
		
		this.worldX = worldCol*gp.tileSize;
		this.worldY = worldRow*gp.tileSize;
		
		this.direction = direction;
		this.speed = speed;
		this.length = length;
		
		solidArea = new Rectangle();
		solidArea.x = 20;
		solidArea.y = 35;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 35;
		solidArea.height = 40;
		
		getLogImage();
		
		logs.add(this);
	}
	
	public void getLogImage() {
		
		try {
			
			left = ImageIO.read(getClass().getResourceAsStream("/log/log_left.png"));
			middle = ImageIO.read(getClass().getResourceAsStream("/log/log_middle.png"));
			right = ImageIO.read(getClass().getResourceAsStream("/log/log_right.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		// IF LOG HAS REACHED LEFT-MOST END OF MAP
		if(direction.equals("left") && worldX + 5*gp.tileSize < gp.tileSize) {
			
			worldX = gp.maxWorldCol*gp.tileSize;
		}
		// IF LOG HAS REACHED RIGHT-MOST END OF MAP
		else if(direction.equals("right") && worldX > gp.maxWorldCol*gp.tileSize) {
			worldX = -4*gp.tileSize;
		}
		// ELSE CONTINUOUSLY MOVE IT
		else {
			if(direction.equals("left")) {
				worldX-=speed;
			}
			else if(direction.equals("right")) {
				worldX+=speed;
			}
		}
			
	}
	
	public void draw(Graphics2D g2) {
		
		screenX = worldX - gp.player.worldX + gp.player.screenX;
		screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		for(int i = 0; i < length; i++) {
			if(i == 0) {
				if(worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
						worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
						worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
						worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
					g2.drawImage(left, screenX+i*gp.tileSize, screenY, gp.tileSize, gp.tileSize, null);
				}
				
			}
			else if(i == length-1) {
				if(worldX+i*gp.tileSize > gp.player.worldX - gp.player.screenX - gp.tileSize &&
						worldX-i*gp.tileSize < gp.player.worldX + gp.player.screenX + gp.tileSize &&
						worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
						worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
					g2.drawImage(right, screenX+i*gp.tileSize, screenY, gp.tileSize, gp.tileSize, null);
				}
			}
			else{
				if(worldX+i*gp.tileSize > gp.player.worldX - gp.player.screenX - gp.tileSize &&
						worldX-i*gp.tileSize < gp.player.worldX + gp.player.screenX + gp.tileSize &&
						worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
						worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
					g2.drawImage(middle, screenX+i*gp.tileSize, screenY, gp.tileSize, gp.tileSize, null);
				}
			}
		}
		
	}

}
