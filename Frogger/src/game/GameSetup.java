package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameSetup extends JPanel {

	private BufferedImage skin1, skin2, skin3;
	JButton button1, button2, button3, dif1, dif2, dif3, done;
	JFrame window;
	JFrame setupWindow;
	GamePanel gp;
	
	// SCREEN SETTINGS
	final int originalTileSize = 16;
	final int scale = 5;
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 7;
	public final int maxScreenRow = 7;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	// INPUTS
	int skin = 1;
	int dif = 1;
	
	public GameSetup(JFrame window, GamePanel gp, JFrame setupWindow) {
		
		this.window = window;
		this.gp = gp;
		this.setupWindow = setupWindow;
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.setLayout(null);
		
		// GET SKIN IMAGES
		
		try {
			skin1 = ImageIO.read(getClass().getResourceAsStream("/player/player_00.png"));
			skin2 = ImageIO.read(getClass().getResourceAsStream("/player/skin_1_00.png"));
			skin3 = ImageIO.read(getClass().getResourceAsStream("/player/skin_2_00.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		// SKIN BUTTONS
		
		button1 = new JButton("Select");
		button1.setBounds((int) (screenWidth/5-tileSize/2), (int) (screenHeight*0.2)+tileSize, 76, 50);
		button1.addActionListener(new ActionListener() {
			
			@Override
	        public void actionPerformed(ActionEvent e) {
	            skin = 1;
	            button1.setText("^^^");
	            button2.setText("Select");
	            button3.setText("Select");
	            
	        } });
		this.add(button1);

		button2 = new JButton("Select");
		button2.setBounds((int) (screenWidth/2-tileSize/2), (int) (screenHeight*0.2)+tileSize, 76, 50);
		button2.addActionListener(new ActionListener() {
			
			@Override
	        public void actionPerformed(ActionEvent e) {
	            skin = 2;
	            button2.setText("^^^");
	            button1.setText("Select");
	            button3.setText("Select");
	            
	        } });
		this.add(button2);
		
		button3 = new JButton("Select");
		button3.setBounds((int) (screenWidth-screenWidth/5-tileSize/2), (int) (screenHeight*0.2)+tileSize, 76, 50);
		button3.addActionListener(new ActionListener() {
			
			@Override
	        public void actionPerformed(ActionEvent e) {
	            skin = 3;
	            button3.setText("^^^");
	            button1.setText("Select");
	            button2.setText("Select");
	            
	        } });
		this.add(button3);
		
		// DIFFICULTY BUTTONS
		
		dif1 = new JButton("Select");
		dif1.setBounds((int) (screenWidth/5-tileSize/2), (int) (screenHeight*0.2)+4*tileSize, 76, 50);
		dif1.addActionListener(new ActionListener() {
			
			@Override
	        public void actionPerformed(ActionEvent e) {
	            dif = 1;
	            dif1.setText("^^^");
	            dif2.setText("Select");
	            dif3.setText("Select");
	            
	        } });
		this.add(dif1);

		dif2 = new JButton("Select");
		dif2.setBounds((int) (screenWidth/2-tileSize/2), (int) (screenHeight*0.2)+4*tileSize, 76, 50);
		dif2.addActionListener(new ActionListener() {
			
			@Override
	        public void actionPerformed(ActionEvent e) {
	            dif = 2;
	            dif2.setText("^^^");
	            dif1.setText("Select");
	            dif3.setText("Select");
	            
	        } });
		this.add(dif2);
		
		dif3 = new JButton("Select");
		dif3.setBounds((int) (screenWidth-screenWidth/5-tileSize/2), (int) (screenHeight*0.2)+4*tileSize, 76, 50);
		dif3.addActionListener(new ActionListener() {
			
			@Override
	        public void actionPerformed(ActionEvent e) {
	            dif = 3;
	            dif3.setText("^^^");
	            dif1.setText("Select");
	            dif2.setText("Select");
	            
	        } });
		this.add(dif3);
		
		// DONE BUTTON
		
		done = new JButton("Start Game!");
		done.setBounds((int) (screenWidth/2-tileSize/2)-163, (int) (screenHeight*0.2)+5*tileSize, 400, 40);
		done.addActionListener(new ActionListener() {
			
			@Override
	        public void actionPerformed(ActionEvent e) {
				startGame();
	            
	        } });
		this.add(done);
		
	}
	
	public void startGame() {
		this.setVisible(false);

		window.add(gp);
		
		gp.setSkin(skin);
		System.out.println(skin);
		gp.setDif(dif);
		System.out.println(dif);
		gp.startGameThread();
		gp.player.getPlayerImage();
		
		setupWindow.setVisible(false);
		
		window.setVisible(true);
	}
	
    public void paintComponent(Graphics g) {
    	
        super.paintComponent(g);
        String text = "Choose Your Skin!";
        int textLength = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(text, screenWidth/2 - textLength-20, screenHeight/7);
        
        g.drawImage((Image)skin1, (int) (screenWidth/5-tileSize/2), (int) (screenHeight*0.2), tileSize, tileSize, null);
        g.drawImage((Image)skin2, (int) (screenWidth/2-tileSize/2), (int) (screenHeight*0.2), tileSize, tileSize, null);
        g.drawImage((Image)skin3, (int) (screenWidth-screenWidth/5-tileSize/2), (int) (screenHeight*0.2), tileSize, tileSize, null);
    
        text = "Choose Your Difficulty!";
        textLength = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(text, screenWidth/2 - textLength/2, screenHeight/7*4);
        
        text = "EASY";
        g.setColor(Color.black);
        textLength = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, (int) (screenWidth/5-tileSize/2), screenHeight/7*5);
        g.setColor(Color.green);
        g.drawString(text, (int) (screenWidth/5-tileSize/2)+2, screenHeight/7*5-2);
        
        text = "MEDIUM";
        g.setColor(Color.black);
        textLength = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, (int) (screenWidth/2-tileSize/2-19), screenHeight/7*5);
        g.setColor(Color.yellow);
        g.drawString(text, (int) (screenWidth/2-tileSize/2-19)+2, screenHeight/7*5-2);
        
        text = "HARD";
        g.setColor(Color.black);
        textLength = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, (int) (screenWidth-screenWidth/5-tileSize/2-3), screenHeight/7*5);
        g.setColor(Color.red);
        g.drawString(text, (int) (screenWidth-screenWidth/5-tileSize/2-3)+2, screenHeight/7*5-2);
    
    
    }
	
}
