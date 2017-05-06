import com.digi.xbee.api.WiFiDevice;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.models.XBeeProtocol;
import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.models.XBee64BitAddress;
import com.digi.xbee.api.models.XBeeMessage;

public class XBeeManager{
    /* Constants */
    // TODO Replace with the port where your sender module is connected to.
    private static final String PORT = "COM4";
    // TODO Replace with the baud rate of your sender module.  
    private static final int BAUD_RATE = 9600;
	static XBeeDevice myDevice = new XBeeDevice(PORT, BAUD_RATE);


    private final String DATA_TO_SEND = "SOS!";
    
    public XBeeManager(){
    }
    /*
    public void main(String[] args){
    	String[] messages = XBeeManager.getMessages();
    	for(int i = 0; i < messages.length; i++){
    		System.out.println(messages[i]);
    	}
    	myDevice.close();
    }
    */
    //This is the function that enables one to broadcast a message to the entire fleet
    public void broadcast(String data){
    	byte[] bytes = data.getBytes();
    	try{
    		myDevice.open();
    		myDevice.sendBroadcastData(bytes);
    		myDevice.close();
    	}
    	catch(XBeeException e){}
    }
    
    //This sends a message to a specified boat
    public void sendMessage(String data, String address){
    	byte[] bytes = data.getBytes();
    	try{
    		myDevice.open();
    		RemoteXBeeDevice myRemoteXBeeDevice = new RemoteXBeeDevice(myDevice,
				    new XBee64BitAddress(address));
    		myDevice.sendDataAsync(myRemoteXBeeDevice, bytes);
    		myDevice.close();
    	}
    	catch(XBeeException e){}
    }
    
    public String[] getMessages(){
    	try{
    		myDevice.open();
        	XBeeMessage message = myDevice.readData(10000);
        	if(message != null){
	        	RemoteXBeeDevice remote = message.getDevice();
	        	byte[] data = message.getData();
	        	if(data.length > 0){
	        		String dataStr = new String(data);
	        		XBee64BitAddress address = remote.get64BitAddress();
	        		if(address != null){
	        			String id = address.toString();
	        			return new String[]{dataStr, id};
	        		}
	        	}
        	}
        	else{
        		myDevice.close();
        		System.out.println("Timeout");
        	}
    	}
    	catch(XBeeException e){}
    	return null;
    }
}