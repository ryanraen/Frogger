package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

import entity.Car;
import entity.Crocodile;
import entity.Log;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16 x 16 tile
	final int scale = 5;
	
	public final int tileSize = originalTileSize * scale; // 80x80 tile
	public final int maxScreenCol = 16; // 4x3 aspect ratio while monitors are usually 1920 x 1080
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 1280 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 960 pixels
	
	// WORLD SETTINGS
	public final int maxWorldCol = 19;
	public final int maxWorldRow = 38;
	
	// FPS
	int FPS = 60;
	int tick = 5; // 120 ticks in one second
	
	// GAME SETTINGS
	public int skin = 1;
	public int dif = 1;
	
	// SYSTEM
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public LogChecker lChecker = new LogChecker(this);
	public CrocodileChecker crocChecker = new CrocodileChecker(this);
	public DeathChecker dChecker = new DeathChecker(this);
	UI ui = new UI(this);
	Thread gameThread;
	
	Random gen = new Random();
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		this.setBackground(new Color(0, 61, 87));
		
		setupGame();
	}
	
	public void setSkin(int skin) {
		this.skin = skin;
	}
	
	public void setDif(int dif) {
		this.dif = dif;
	}
	
	public void setupGame() {
		
		// INITIALIZE LOGS
		for(int i = 24; i < 30; i++) {
			String direction = i%2==0?"right":"left";
			int logX = direction.equals("left")?gen.nextInt(maxWorldCol/2):gen.nextInt(maxWorldCol/2)+maxWorldCol/2; // LOGX VARIES 0-18
			int speed = gen.nextInt(3)+dif*3; // SPEED VARIES 1-3
			int length1 = gen.nextInt(3)+2; // LOG LENGTH 2-4 (FIRST LOG)
			
			Log.logs.add(new Log(this, direction, logX, i, speed, length1));
			
			int length2 = gen.nextInt(3)+3; // SECOND LOG LENGTH
			
			if(direction.equals("left")) {
				Log.logs.add(new Log(this, direction, logX+length1+maxWorldCol/2, i, speed, length2));
			}
			else {
				Log.logs.add(new Log(this, direction, logX-maxWorldCol/2, i, speed, length2));
			}
			
		}
		
		// INITIALIZE CARS
		for(int i = 16; i < 22; i++) {
			String direction = i<33?"right":"left";
			int carX = direction.equals("left")?gen.nextInt(maxWorldCol/2):gen.nextInt(maxWorldCol/2)+maxWorldCol/2; // CARX VARIES 0-18
			int speed = gen.nextInt(4)+2+dif; // SPEED VARIES 3-6
			
			Car.cars.add(new Car(this, direction, carX, i, speed));
			
			if(direction.equals("left")) {
				Car.cars.add(new Car(this, direction, carX+tileSize+maxWorldCol/2, i, speed));
			}
			else {
				Car.cars.add(new Car(this, direction, carX-maxWorldCol/2, i, speed));
			}
			System.out.println(Car.cars.size());
		}
		
		// INITIALIZE CROCODILES
		for(int i = 8; i < 14; i++) {
			String direction = i%2==0?"right":"left";
			int crocX = direction.equals("left")?gen.nextInt(maxWorldCol/2):gen.nextInt(maxWorldCol/2)+maxWorldCol/2; // CROCODILEX VARIES 0-18
			int speed = gen.nextInt(3)+dif*2; // SPEED VARIES 1-3
			// *** LENGTH IS ALWAYS 3 (HEAD, TORSO, TAIL, OF CROCODILE)
			
			Crocodile.crocodiles.add(new Crocodile(this, direction, crocX, i, speed));
			
			if(direction.equals("left")) {
				Crocodile.crocodiles.add(new Crocodile(this, direction, crocX+3+maxWorldCol/2, i, speed));
			}
			else {
				Crocodile.crocodiles.add(new Crocodile(this, direction, crocX-maxWorldCol/2, i, speed));
			}
			
		}
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		// GAME LOOP
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) { // every 1/60 of a second -> 60 fps
				
				// UPDATE: update information such as character position
				update();
				
				// DRAW: draw the screen with the updated information
				repaint();
				
				delta--;
				drawCount++;
				
				if(drawCount % tick == 0 && player.canMoveByTick == false) {
					player.canMoveByTick = true;
				}
				
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
		}
		
	}
	
	public void update() {
		
		lChecker.update();
		crocChecker.update();
		
		for(int i = 0; i < Log.logs.size(); i++) {
			Log.logs.get(i).update();
		}
		
		for(int i = 0; i < Car.cars.size(); i++) {
			Car.cars.get(i).update();
		}
		
		for(int i = 0; i < Crocodile.crocodiles.size(); i++) {
			Crocodile.crocodiles.get(i).update();
		}
		
		player.update();
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		
		// TILES		
		tileM.draw(g2); // must be before player.draw(g2) for player to be on top !!
		
		// LOGS
		for(int i = 0; i < Log.logs.size(); i++) {
			Log.logs.get(i).draw(g2);
		}
		
		for(int i = 0; i < Car.cars.size(); i++) {
			Car.cars.get(i).draw(g2);
		}
		
		for(int i = 0; i < Crocodile.crocodiles.size(); i++) {
			Crocodile.crocodiles.get(i).draw(g2);
		}
		
		// PLAYER
		player.draw(g2);
		
		// CAR TUNNEL (FOREGROUND)
		tileM.drawCarTunnel(g2);
		
		// UI
		ui.draw(g2);
		
		g2.dispose();
	}

}
