package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import game.GamePanel;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[30]; // max number of tiles 
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		readMap("/levels/map.txt");
		
	}
	
	public void getTileImage() {
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[0].collision = true;
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));
			
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[8].collision = true;
			
			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water_deep.png"));
			
			tile[10] = new Tile();
			tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water_edge_left.png"));
			
			tile[11] = new Tile();
			tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water_edge_right.png"));
			
			tile[12] = new Tile();
			tile[12].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_left.png"));
			tile[12].collision = true;
			
			tile[13] = new Tile();
			tile[13].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_left_down.png"));
			tile[13].collision = true;
			
			tile[14] = new Tile();
			tile[14].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_down.png"));
			tile[14].collision = true;
			
			tile[15] = new Tile();
			tile[15].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand_up.png"));
			
			tile[16] = new Tile();
			tile[16].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand_down.png"));
			
			tile[17] = new Tile();
			tile[17].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand_left_down.png"));
			
			tile[18] = new Tile();
			tile[18].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand_left_up.png"));
			
			tile[19] = new Tile();
			tile[19].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road_up.png"));
			
			tile[20] = new Tile();
			tile[20].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road_middle.png"));
			
			tile[21] = new Tile();
			tile[21].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road_down.png"));
			
			tile[22] = new Tile();
			tile[22].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand_left.png"));
			
			tile[23] = new Tile();
			tile[23].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand_right.png"));
			
			tile[24] = new Tile();
			tile[24].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand_right_up.png"));
			
			tile[25] = new Tile();
			tile[25].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand_right_down.png"));
			
			tile[26] = new Tile();
			tile[26].image = ImageIO.read(getClass().getResourceAsStream("/tiles/finish.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void readMap(String filePath) {
		
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();
				
				while(col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		} catch(Exception e) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		
		
		// DRAW TILES
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			double screenX = worldX - gp.player.worldX + gp.player.screenX;
			double screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if(worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
					worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
					worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
					worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
				
				g2.drawImage(tile[tileNum].image, (int)screenX, (int)screenY, gp.tileSize, gp.tileSize, null);
			}
			
			worldCol++;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
	
	public void drawCarTunnel(Graphics2D g2) {
		
		int worldRow = 16;
		
		while(worldRow < 22) {
			
			int worldY = worldRow * gp.tileSize;
			double screenX = gp.tileSize - gp.player.worldX + gp.player.screenX;
			double screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if(0 > gp.player.worldX - gp.player.screenX - gp.tileSize &&
					0 < gp.player.worldX + gp.player.screenX + gp.tileSize &&
					worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
					worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
				
				if(worldRow == 21) {
					g2.drawImage(tile[14].image, (int)screenX, (int)screenY, gp.tileSize, gp.tileSize, null);
				}
				else {
					g2.drawImage(tile[0].image, (int)screenX, (int)screenY, gp.tileSize, gp.tileSize, null);
				}
			}
			
			screenX = (gp.maxScreenCol+1)*gp.tileSize - gp.player.worldX + gp.player.screenX;
			screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if(gp.maxScreenCol*gp.tileSize > gp.player.worldX - gp.player.screenX - gp.tileSize &&
					gp.maxScreenCol*gp.tileSize < gp.player.worldX + gp.player.screenX + gp.tileSize &&
					worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
					worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
				
				if(worldRow == 21) {
					g2.drawImage(tile[13].image, (int)screenX, (int)screenY, gp.tileSize, gp.tileSize, null);
				}
				else {
					g2.drawImage(tile[12].image, (int)screenX, (int)screenY, gp.tileSize, gp.tileSize, null);
				}
			}
			
			worldRow++;
		}
	}
	
//	public void drawOuterWalls(Graphics2D g2) {
//
//		int worldRow = 0;
//		
//		while(worldRow < gp.maxWorldRow) {
//			
//			if(worldRow < 44 && worldRow > 37) {
//				break;
//			}
//			
//			int worldY = worldRow * gp.tileSize;
//			double screenX = 0 - gp.player.worldX + gp.player.screenX;
//			double screenY = worldY - gp.player.worldY + gp.player.screenY;
//			
//			if(0 > gp.player.worldX - gp.player.screenX - gp.tileSize &&
//					0 < gp.player.worldX + gp.player.screenX + gp.tileSize &&
//					worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
//					worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
//				
//				g2.drawImage(tile[12].image, (int)screenX, (int)screenY, gp.tileSize, gp.tileSize, null);
//			}
//			
//			screenX = gp.maxScreenCol*gp.tileSize - gp.player.worldX + gp.player.screenX;
//			screenY = worldY - gp.player.worldY + gp.player.screenY;
//			
//			if(gp.maxScreenCol*gp.tileSize > gp.player.worldX - gp.player.screenX - gp.tileSize &&
//					gp.maxScreenCol*gp.tileSize < gp.player.worldX + gp.player.screenX + gp.tileSize &&
//					worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
//					worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
//				
//				g2.drawImage(tile[12].image, (int)screenX, (int)screenY, gp.tileSize, gp.tileSize, null);
//			}
//			
//			worldRow++;
//		}
//		
//	}

}
