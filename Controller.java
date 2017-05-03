// The contents of this file are dedicated to the public domain.
// (See http://creativecommons.org/publicdomain/zero/1.0/)

import java.awt.Graphics;
import java.io.IOException;
import javax.swing.Timer;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Random;
import java.util.Comparator;
import java.util.Arrays;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements MouseListener, KeyListener
{
	//TODO
	//This agent needs to be removed
	//Agent agent;
	Model model; // holds all the game data
	View view; // the GUI
	LinkedList<MouseEvent> mouseEvents; // Queue of mouseEvents
	LinkedList<KeyEvent> keyEvents;	// Queue of keyEvents

	Controller()
	{
		this.mouseEvents = new LinkedList<MouseEvent>();
		this.keyEvents = new LinkedList<KeyEvent>();
	}

	void init() throws Exception
	{
		this.model = new Model(this);
		this.model.initGame();
	}

	boolean update()
	{
		model.update();
		return true;
	}

	Model getModel() { return model; }

	MouseEvent nextMouseEvent()
	{
		//System.out.println("nextMouseEvent");
		if(mouseEvents.size() == 0)
			return null;
		return mouseEvents.remove();
	}

	KeyEvent nextKeyEvent()
  {
		//System.out.println("nextKeyEvent");
    if(keyEvents.size() == 0){
      return null;
    }
    return keyEvents.remove();
  }

	public void mousePressed(MouseEvent e)
	{
		System.out.println("Got a mouse event");
			mouseEvents.add(e);
			if(mouseEvents.size() > 20){ // discard events if the queue gets big
				mouseEvents.remove();
			}
	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }

	public void keyTyped(KeyEvent e)
  {
		System.out.println(e.getKeyChar());
    keyEvents.add(e);
		if(keyEvents.size() > 20){ // discard events if the queue gets big
			keyEvents.remove();
		}
  }

  public void keyPressed(KeyEvent e) {}
  public void keyReleased(KeyEvent e) {}
}
