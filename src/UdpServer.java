import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;


public class UdpServer extends Thread {
	//All are global variables
	DatagramSocket socketUDP;
	DatagramPacket packetUDP;
	int portLocal;
	byte[] tamponAccuse;
	byte[] tampon;
	String texte;
	List<Integer> data;
	int longueurAccuse;
	
    public UdpServer() { 
    	// here the constructor of my new object UdpServer
        tamponAccuse = "Message reçu.".getBytes();
        longueurAccuse = tamponAccuse.length;
        portLocal = 2014; //we can change this value.
        tampon = new byte[256];
        texte = "Aucun message reçu";
        data = new ArrayList();
    }
   
    public void run() {
        try {
        	System.out.println("Lancement du serveur(1)");
            socketUDP = new DatagramSocket(portLocal);
            System.out.println("Lancement du serveur(2)");
    		Thread closingHook = new Thread(() -> socketUDP.close());
    		System.out.println("Lancement du serveur(3)");
    		Thread printingHook = new Thread(() -> System.out.println("On a bien fermé le serveur."));
    		System.out.println("serveur lancé.");
    		//All this sys.out permits to see where was the problem if the method start fail.
    		
            while(true) {

            	packetUDP = new DatagramPacket(tampon, tampon.length);
                socketUDP.receive(packetUDP);
                InetAddress adresseIP = packetUDP.getAddress();
                int portDistant = packetUDP.getPort();
                texte = new String(tampon); 
                texte = texte.substring(0, packetUDP.getLength()); 
                
                for (int i = 0; i < tampon.length; i++) {
                	data.add(tampon[i] & 0xFF);
                	//This line permits to convert my byte in an integer. 
                	//I know it doesn't work and i have to improve my conversion.
                	
                }
                
                System.out.println("le tampon :");
                System.out.println(tampon);
                System.out.println("Reception du port " + portDistant + " de la machine " + adresseIP.getHostName() + " : " + texte);
                tampon = new byte[256];
                
                // We send an accused of reception
                packetUDP = new DatagramPacket(tamponAccuse, longueurAccuse, adresseIP, portDistant);
                socketUDP.send(packetUDP);
            }
        }
        catch(Exception exc) {
            System.out.println("Probleme d'ouverture du serveur");
        }
    }
    
    public void closeserveur() {
    	//the method for closing my server
        try {
    		socketUDP.close();
        }
        catch(Exception exc) {
            System.out.println("Probleme de fermeture du serveur");
        }
    }
    
    //for having the text message (if we receive a message)
    //In our case we are just going to use the method getData because we want to fill the chart with
    //the date of our temperature sensor.
    public String getMessage() {
    	return texte;
    }
    
    //for having the data of our temperature sensor.
    public List<Integer> getData() {
    		return data;    
    
    }
}