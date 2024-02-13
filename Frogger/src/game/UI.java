package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

public class UI {
	
	GamePanel gp;
	Font arial_50B, arial_90B;
	
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameLost = false;
	public boolean gameWon = false;
	
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_50B = new Font("Arial", Font.BOLD, 50);
		arial_90B = new Font("Arial", Font.BOLD, 90);

	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
		
	}
	public void draw(Graphics2D g2) {
		
		if(gameLost) {
			
			g2.setFont(arial_90B);
			g2.setColor(Color.black);
			
			String text;
			int textLength;
			int x;
			int y;
			
			text = "YOU DIED";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - (gp.tileSize*3);
			g2.drawString(text, x, y);
			
			
			text = "Your Time Ended At: " + dFormat.format(playTime) + "!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*4);
			g2.drawString(text, x, y);
			
			
			
			g2.setFont(arial_90B);
			g2.setColor(Color.white);
			
			text = "YOU DIED";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2 + 3;
			y = gp.screenHeight/2 - (gp.tileSize*3) - 3;
			g2.drawString(text, x, y);
			
			
			text = "Your Time Ended At: " + dFormat.format(playTime) + "!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2 + 3;
			y = gp.screenHeight/2 + (gp.tileSize*4) - 3;
			g2.drawString(text, x, y);
			
			gp.gameThread = null;
			
		}
		else if(gameWon) {
			
			g2.setFont(arial_90B);
			g2.setColor(Color.yellow);
			
			String text;
			int textLength;
			int x;
			int y;
			
			g2.setFont(arial_90B);
			g2.setColor(Color.black);
			text = "Congratulations!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*2);
			g2.drawString(text, x, y);
			
			text = "Your Time is: " + dFormat.format(playTime) + "!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*4);
			g2.drawString(text, x, y);
			
			g2.setColor(Color.yellow);
			text = "Congratulations!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2 + 3;
			y = gp.screenHeight/2 + (gp.tileSize*2) - 3;
			g2.drawString(text, x, y);
			
			text = "Your Time is: " + dFormat.format(playTime) + "!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2 + 3;
			y = gp.screenHeight/2 + (gp.tileSize*4) - 3;
			g2.drawString(text, x, y);
			
			gp.gameThread = null;
		}
		else {
			
			g2.setFont(arial_50B);
			g2.setColor(Color.white);
			
			// TIME
			playTime +=(double)1/60;
			g2.drawString("Time:" + dFormat.format(playTime), gp.tileSize*12, 97);

			// MESSAGE
			if(messageOn) {
				
				g2.setFont(g2.getFont().deriveFont(30F));
				g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
				
				messageCounter++;
				
				if(messageCounter > 120) {
					messageCounter = 0;
					messageOn = false;
				}
			}
			
		}
		

	}

}
