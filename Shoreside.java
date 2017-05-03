import java.util.Scanner;
import javax.swing.Timer;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

class Shoreside
{
  private void initMessage()
  {
    System.out.println();
    System.out.println("Welcome to the Shoreside Program");
    System.out.println("This will serve as the main communication to the fleet.");
    System.out.println();
    System.out.println();
    return;
  }

  public char menuChoice(Scanner in)
  {
    String input;
    char pInput;

    System.out.println("Here are you options:");
    System.out.println();
    System.out.println("Enter 'f' to enter Fleet Command Mode");
    System.out.println();
    System.out.println("Enter '1', '2', '3', '4' to select an individual boat and enter Boat Command Mode");
    System.out.println();

    input = in.nextLine();

    //Process input for a the char
    //Should be one char
    pInput = input.charAt(0);

    return pInput;
  }

  //TODO
  //Mark's old gpsConverter. Needs to be updated using the website.
  public String gpsConverter(int x, int y)
	{

		double new_x = 26.1 - (.0095698925*(double)x);
		double new_y = 15.5 - (.0106299213*(double)y);
		String gps_x = "948"+ Double.toString(new_x);
		String gps_y = "368"+ Double.toString(new_y);
		System.out.println(gps_y);
		System.out.println(gps_x);

		return "";
	}

  public static void main(String[] args) throws Exception
  {
    //Local Variables
    Scanner in;
    Shoreside s;
    char choice;
    Controller c;
    Model m;
    KeyEvent keepMe;

    //Initialize the Controller
    c = new Controller();
    c.init();

    // instantiates a JFrame, which spawns another thread to pump events and keeps the whole program running until the JFrame is closed
    c.view = new View(c, c.model);

    // creates an ActionEvent at regular intervals, which is handled by View.actionPerformed
    new Timer(20, c.view).start();

    //Bring a pointer to the model to this context
    m = c.getModel();

    m.addBoat(100, 100);
    m.addBoat(200, 200);
    m.addBoat(200, 300);
    m.addBoat(300, 300);
    //m.addObstacle(300, 300);

    //Initialize a Shoreside object
    s = new Shoreside();

    //Initialize keepMe to null
    keepMe = null;

    //Initialize scanner to read in from the command line
    //in = new Scanner(System.in);

    //Print initial message
    s.initMessage();

    while(true){
      //Check for MouseEvent and KeyEvents first
      //System.out.println("LLLLOOOOPPP");
      KeyEvent k = c.nextKeyEvent();
      //System.out.println("Made it 2");

      if(k == null){
        //Do nothing?
        //System.out.println("K is null");
      }
      else if(k.getKeyChar() == '1'){
        //System.out.println("Got the key");
        keepMe = k;
      }
      else if(k.getKeyChar() == '2'){
        keepMe = k;
      }
      else if(k.getKeyChar() == '3'){
        keepMe = k;
      }
      else if(k.getKeyChar() == '4'){
        keepMe = k;
      }
      else{
        System.out.println("Invalid key pressed");
      }

      MouseEvent e = c.nextMouseEvent();
      if(e == null){
        //Do nothing?
      }
      else if(e.getButton() == MouseEvent.BUTTON1){
        if(keepMe != null){
          if(keepMe.getKeyChar() == '1'){
            //System.out.println("Boat 1 should move");
            m.setDestinationBoat(e.getX(), e.getY(), 0);
            //Reset keepMe to null
            keepMe = null;
          }
          else if(keepMe.getKeyChar() == '2'){
            m.setDestinationBoat(e.getX(), e.getY(), 1);
            //Reset keepMe to null
            keepMe = null;
          }
          else if(keepMe.getKeyChar() == '3'){
            m.setDestinationBoat(e.getX(), e.getY(), 2);
            //Reset keepMe to null
            keepMe = null;
          }
          else if(keepMe.getKeyChar() == '4'){
            m.setDestinationBoat(e.getX(), e.getY(), 3);
            //Reset keepMe to null
            keepMe = null;
          }
        }
      }
      else if(e.getButton() == MouseEvent.BUTTON3){
        //System.out.println("Right click");
				m.setDestinationFleet(e.getX(), e.getY());
			}
      //System.out.println("Made it");

      //System.out.println("End of Loop");

      c.update();
    }
  }
}
