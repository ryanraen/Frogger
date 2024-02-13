package game;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		JFrame window = new JFrame();
		JFrame game = new JFrame();
		
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Game Settings");
		
		GamePanel gamePanel = new GamePanel();
		
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setResizable(false);
		game.setTitle("Game Settings");

		game.add(gamePanel);
		game.pack();
		game.setLocationRelativeTo(null);
		game.setVisible(false);
		
		GameSetup gameSetup = new GameSetup(game, gamePanel, window);
		window.add(gameSetup);
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);	
		
	}

}
