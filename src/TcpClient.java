import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient {
  final static int port = 135;

  public static void main(String[] args) {

    DataInputStream userInput;
    PrintStream theOutputStream;

    try {
      InetAddress serveur = InetAddress.getByName("10.10.25.53");
      System.out.println("Connexion...");
      Socket socket = new Socket(serveur, port);
      System.out.println("Connexion établie !");
      

      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintStream out = new PrintStream(socket.getOutputStream());

      out.println(args[1]);
      System.out.println(in.readLine());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}