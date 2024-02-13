package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import game.GamePanel;

public class Car extends Entity{

	GamePanel gp;
	Random gen;
	
	public BufferedImage image;
	public int screenX;
	public int screenY;
	public static ArrayList<Car> cars = new ArrayList<Car>();
	
	public Car(GamePanel gp, String direction, int worldCol, int worldRow, int speed) {

		this.gp = gp;
		gen = new Random();
		
		this.worldX = worldCol*gp.tileSize;
		this.worldY = worldRow*gp.tileSize;
		
		this.direction = direction;
		this.speed = speed;
		
		solidArea = new Rectangle();
		solidArea.x = 20;
		solidArea.y = 35;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 35;
		solidArea.height = 40;
		
		getCarImage();
		
		cars.add(this);
		
	}

	public void getCarImage() {
		
		try {
			
			// PICK ONE FROM 3 CAR IMAGES
			int choice = gen.nextInt(4)+1;
			switch(choice) {
			case 1:
				image = ImageIO.read(getClass().getResourceAsStream("/car/car_1_" + direction + ".png"));
				break;
			case 2:
				image = ImageIO.read(getClass().getResourceAsStream("/car/car_2_" + direction + ".png"));
				break;
			case 3:
				image = ImageIO.read(getClass().getResourceAsStream("/car/car_3_" + direction + ".png"));
				break;
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		// IF CAR HAS REACHED LEFT-MOST END OF MAP
		if(direction.equals("left") && worldX <= gp.tileSize) {
			// RANDOMIZE CAR IMAGE EACH TIME IT RESPAWNS
			getCarImage();
			worldX = gp.maxWorldCol*gp.tileSize-2*gp.tileSize;
		}
		// IF CAR HAS REACHED RIGHT-MOST END OF MAP
		else if(direction.equals("right") && worldX+gp.tileSize >= gp.maxWorldCol*gp.tileSize-gp.tileSize) {
			getCarImage();
			worldX = gp.tileSize;
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
		
		if(worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
				worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
				worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
				worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}
	
}
