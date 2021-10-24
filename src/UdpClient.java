import java.net.*;

public class UdpClient {

  final static int port = 1000;
  final static int taille = 1024;
  static byte buffer[] = new byte[taille];

  public static void main(String argv[]) throws Exception {
    try {
      System.out.println("demarrage");
      ///String hostname = "10.10.26.102";
      
      InetAddress serveur = InetAddress.getByName("10.10.26.102");
      int length = 512;
      byte buffer[] = new byte[length];
      
      buffer = "c'est bon ça marche ".getBytes();
      
      DatagramSocket socket = new DatagramSocket();
      DatagramPacket donneesEmises = new DatagramPacket(buffer, buffer.length, serveur, port);
      DatagramPacket donneesRecues = new DatagramPacket(new byte[taille], taille);

      socket.send(donneesEmises);
      System.out.println("Données envoyées");
      socket.receive(donneesRecues);

      System.out.println("Message : " + new String(donneesRecues.getData(), 
        0, donneesRecues.getLength()));
      System.out.println("de : " + donneesRecues.getAddress() + ":" + 
        donneesRecues.getPort());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

	
