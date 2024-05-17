import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    private static final BigInteger G = BigInteger.valueOf(13);
    private static final BigInteger P = BigInteger.valueOf(5);
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(3309);
            Socket clientSocket = serverSocket.accept();

            ObjectOutputStream serverOutput = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream serverInput = new ObjectInputStream(clientSocket.getInputStream());

            String message =(String) serverInput.readObject();
            System.out.println(STR."Accepted Message: \{message}");
            serverOutput.writeObject(message);

            // ~~~~~~~~~~~~~~~~~
            // Communication and Calculation
            BigInteger A = BigInteger.valueOf((long)Util.generateRandom(0,1000));

            BigInteger calculatedServerValue = G.modPow(A, P);
            System.out.println(STR."Message sent to the client is: \{calculatedServerValue}");
            serverOutput.writeObject(calculatedServerValue);

            BigInteger valueFromClient = (BigInteger) serverInput.readObject();
            System.out.println(STR."Value accepted from the client is: \{valueFromClient}");

            // Final key
            BigInteger exchagedKey = valueFromClient.modPow(A, P);
            System.out.println(STR."Final key is: \{exchagedKey}");
            // ~~~~~~~~~~~~~~~~~

            serverSocket.close();
            clientSocket.close();
        } catch (UnknownHostException UHE) {
            System.err.println("IP address could not be determined.");
        } catch (IOException IOE) {
            System.err.println("Error with Input/Output.");
        } catch (ClassNotFoundException CNFE) {
            System.err.println("Class from Server Input not found.");
        }
    }
}
