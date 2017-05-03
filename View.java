// The contents of this file are dedicated to the public domain.
// (See http://creativecommons.org/publicdomain/zero/1.0/)

import javax.swing.JFrame;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.awt.Image;
import java.util.ArrayList;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.event.WindowEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;

public class View extends JFrame implements ActionListener {
	Controller controller;
	Model model;
	private MyPanel panel;
	private ArrayList<Controller> replayPoints;

	public View(Controller c, Model m) throws Exception {
		this.controller = c;
		this.model = m;

		// Make the game window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Shoreside");
		this.setSize(930, 762);
		this.panel = new MyPanel();
		//MAGIC
		this.panel.setFocusable(true);
		//this.panel.requestFocusInWindow();
		//END MAGIC
		this.panel.addMouseListener(controller);
		this.panel.addKeyListener(controller);
		this.getContentPane().add(this.panel);
		this.setVisible(true);

		this.replayPoints = new ArrayList<Controller>();
	}

	public void actionPerformed(ActionEvent evt) {
		repaint(); // indirectly calls MyPanel.paintComponent
	}

	class MyPanel extends JPanel {
		public static final int FLAG_IMAGE_HEIGHT = 22;

		Image imageBoat;
		Image obstacle;

		MyPanel() throws Exception {
			this.imageBoat = ImageIO.read(new File("boat_80x22.png"));
			this.obstacle = ImageIO.read(new File("Obstacle.png"));
		}

		void drawBoats(Graphics g) {
			ArrayList<Model.Boat> boats = model.getBoats();
			for(int i = 0; i < boats.size(); i++) {
				//Draw boats
				Model.Boat s = boats.get(i);
				g.drawImage(imageBoat, (int)s.x - 20, (int)s.y - 5, null);
			}
		}

		void drawObstacles(Graphics g)
		{
			ArrayList<Model.Obstacle> obstacles = model.getObstacles();
			for(int i = 0; i < obstacles.size(); i++){
				//Draw obstacles
				Model.Obstacle o = obstacles.get(i);
				g.drawImage(obstacle, (int)o.x, (int)o.y, null);
			}
		}

		public void paintComponent(Graphics g) {

			// Give the agents a chance to make decisions
			/*
			if(!controller.update()) {
				View.this.dispatchEvent(new WindowEvent(View.this, WindowEvent.WINDOW_CLOSING)); // Close this window
			}
			*/
			// Draw the view
			g.drawImage(model.actualTerrain, 0, 0, null);
			drawBoats(g);
			drawObstacles(g);
		}
	}
}
