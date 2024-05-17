import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * TIME-BASED ONE-TIME PASSWORD AUTHENTICATION
 * SOCKET
 * DIFFIE HELLMAN
 *
 */
public class Client {
    private static final BigInteger G = BigInteger.valueOf(13);
    private static final BigInteger P = BigInteger.valueOf(5);
    public static void main(String[] args) {
        try{
            Socket socket = new Socket("Localhost", 3309);
            ObjectOutputStream clientOutput = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream clientInput = new ObjectInputStream(socket.getInputStream());

            System.out.println("Client is sending a message to the server");
            clientOutput.writeObject("The message for the server");

//            System.out.println(STR."Objekti qe iu dergua serverit & u kthye: \{clientInput.readObject()}");

            // ~~~~~~~~~~~~~~~~~
            BigInteger B = BigInteger.valueOf((long) Util.generateRandom(0, 1000));

            BigInteger calculatedClientValue = G.modPow(B, P);

            BigInteger valueFromServer = (BigInteger) clientInput.readObject();
            System.out.println(STR."Message accepted by the server: \{valueFromServer}");

            System.out.println(STR."Value sent to the server: \{calculatedClientValue}");
            clientOutput.writeObject(calculatedClientValue);

            // Final Key
            BigInteger exchagedKey = valueFromServer.modPow(B, P);
            System.out.println(STR."Final key is: \{exchagedKey}");
            // ~~~~~~~~~~~~~~~~~

            socket.close();
        } catch (UnknownHostException UHE) {
            System.err.println("IP address could not be determined.");
        } catch (IOException IOE) {
            System.err.println("Check the socket. Couldn't create.");
//            IOE.printStackTrace();
        } catch (ClassNotFoundException CNFE) {
            System.err.println("Class from Server Input not found.");
        }
    }
}