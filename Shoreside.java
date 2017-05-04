import javax.swing.Timer;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class Shoreside
{
  class Boat
  {
    int id;
    String XbeeId;

    Boat(int idIn, String XbeeIdIn)
    {
      id = idIn;
      XbeeId = XbeeIdIn;
    }
  }

  private void initMessage()
  {
    System.out.println();
    System.out.println("Welcome to the Shoreside Program");
    System.out.println("This will serve as the main communication to the fleet.");
    System.out.println();
    System.out.println();
    return;
  }

  //TODO
  //Equation needs updated
  public void gpsConverter(int x, int y)
	{

		double newX;
		double newY;
		String gpsX;
		String gpsY;
		String finalGPSX;
		String finalGPSY;

		newX = 0.1405833333 - (0.000002658303441*(double)x);
		newY = 0.1375488889 - (0.000002952755906*(double)y);
		gpsX = 94 + newX;
		gpsY = 36 + newY;
		finalGPSX = gpsX.substring(0, 9);
		finalGPSY = gpsY.substring(0, 9);

		//System.out.println(gpsX.length());
		//System.out.println(gpsY.length());
		//System.out.println(finalGPSX.length());
		//System.out.println(finalGPSY.length());


		//System.out.println(gpsY);
		//System.out.println(gpsX);
		System.out.println(finalGPSX);
		System.out.println(finalGPSY);

		//return "";

	}

  	public void sendBroadcast(String message, XBeeManager xbm)
  	{
  		if(message == null){
  			System.out.println("Don't BROADCAST null messages");
  		}
  		else{
  			xbm.broadcast(message);
  			System.out.println("Broadcasted: " + message);
  		}
  	}

  	public void sendBoatDest(String dest, int id)
  	{
      //TODO
  	}

  	public void pollMessages(XBeeManager xbm)
  	{
      //TODO
      String[] message;
      message = null;

      //message = xbm.getMessages();

      //TODO
      //Handle null message how?
      if(message == null){
        System.out.println("Null Message");
        return;
      }

      //Parse Data from message
      this.parseData(message[0]);

      return;

  	}

    public void addBoat()
    {

    }

    public void parseData(String in)
    {
      //If boat
        //if boat exists
          //update boat coordinates
        //else
          //add new boat to arraylist
      //else if obstacle
        //if obstacle already exists
          //Just say it already exists in terminal
        //Add new obstacle

    }

  public static void main(String[] args) throws Exception
  {
    //Local Variables
    Shoreside s;
    Controller c;
    Model m;
    KeyEvent keepMe;
    XBeeManager xbm;
    ArrayList<Boat> boats;

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

    //Initialize XBeeManager
    xbm = new XBeeManager();

    //Initialize keepMe to null
    keepMe = null;

    //Initialize boats arraylist
    boats = new ArrayList<Boat>();

    //Initialize scanner to read in from the command line
    //in = new Scanner(System.in);

    //Print initial message
    s.initMessage();



    while(true){
      //Poll boats
      //s.pollMessages(xbm);

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
            s.gpsConverter(e.getX(), e.getY());
            //Reset keepMe to null
            keepMe = null;
          }
          else if(keepMe.getKeyChar() == '2'){
            m.setDestinationBoat(e.getX(), e.getY(), 1);
            s.gpsConverter(e.getX(), e.getY());
            //Reset keepMe to null
            keepMe = null;
          }
          else if(keepMe.getKeyChar() == '3'){
            m.setDestinationBoat(e.getX(), e.getY(), 2);
            s.gpsConverter(e.getX(), e.getY());
            //Reset keepMe to null
            keepMe = null;
          }
          else if(keepMe.getKeyChar() == '4'){
            m.setDestinationBoat(e.getX(), e.getY(), 3);
            s.gpsConverter(e.getX(), e.getY());
            //Reset keepMe to null
            keepMe = null;
          }
        }
      }
      else if(e.getButton() == MouseEvent.BUTTON3){
    	  //System.out.println("Right click");
				m.setDestinationFleet(e.getX(), e.getY());
      }

      c.update();
    }
  }
}
