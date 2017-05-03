// The contents of this file are dedicated to the public domain.
// (See http://creativecommons.org/publicdomain/zero/1.0/)

import java.awt.Graphics;
import java.io.File;
import java.util.Random;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.imageio.ImageIO;

class Model {
	public static final float EPSILON = 0.0001f; // A small number
	public static final float XMAX = 929.0f - EPSILON; // The maximum horizontal screen position. (The minimum is 0.)
	public static final float YMAX = 800.0f - EPSILON; // The maximum vertical screen position. (The minimum is 0.)

	private Controller controller;
	private ArrayList<Boat> boats;
	private ArrayList<Obstacle> obstacles;

	public BufferedImage actualTerrain;

	Model(Controller c)
	{
		this.controller = c;
	}

	void initGame() throws Exception
	{
		actualTerrain = ImageIO.read(new File("ActualMap.png"));
		if(actualTerrain.getWidth() != 929 || actualTerrain.getHeight() != 724){
			throw new Exception("Expected the terrain image to have dimensions of 929-by-724");
		}

		boats = new ArrayList<Boat>();
		obstacles = new ArrayList<Obstacle>();
	}

	void addBoat(float x, float y)
	{
		boats.add(new Boat(x, y));
	}

	void addObstacle(float x, float y)
	{
		obstacles.add(new Obstacle(x, y));
	}

	// These methods are used internally. They are not useful to the agents.
	ArrayList<Boat> getBoats() { return this.boats; }

	ArrayList<Obstacle> getObstacles() { return this.obstacles; }

	void update()
	{
		// Update the agents
		for(int i = 0; i < boats.size(); i++) {
			boats.get(i).update();
			//System.out.println("Why no move");
		}
	}

	// 0 <= x < MAP_WIDTH.
	// 0 <= y < MAP_HEIGHT.
	Controller getController() { return controller; }
	float getX() { return boats.get(0).x; }
	float getY() { return boats.get(0).y; }
	float getDestinationX() { return boats.get(0).xDestination; }
	float getDestinationY() { return boats.get(0).yDestination; }

	void setDestinationBoat(float x, float y, int id)
	{
		Boat s = boats.get(id);
		s.xDestination = x;
		s.yDestination = y;
	}

	void setDestinationFleet(float x, float y)
	{
		for(int i = 0; i < boats.size(); i++){
			boats.get(i).xDestination = x;
			boats.get(i).yDestination = y;
		}
	}

	class Boat
	{
		float x;
		float y;
		float xDestination;
		float yDestination;

		Boat(float x, float y)
		{
			this.x = x;
			this.y = y;
			this.xDestination = x;
			this.yDestination = y;
		}

		void update()
		{
			float speed = 0.0001f;
			float dx = this.xDestination - this.x;
			float dy = this.yDestination - this.y;
			float dist = (float)Math.sqrt(dx * dx + dy * dy);
			float t = speed / Math.max(speed, dist);
			dx *= t;
			dy *= t;
			this.x += dx;
			this.y += dy;
			this.x = Math.max(0.0f, Math.min(XMAX, this.x));
			this.y = Math.max(0.0f, Math.min(YMAX, this.y));
		}
	}

	class Obstacle
	{
		float x;
		float y;

		Obstacle(float xIn, float yIn)
		{
			this.x = xIn;
			this.y = yIn;
		}
	}
}
