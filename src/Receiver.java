import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Receiver implements Runnable {

    final Node              _RefNode;
    final ServerSocket      _Socket;
    boolean                 _IsRunning;

    Receiver(Node parentNode, String hostName, int portNum, int connectionBackLog) throws IOException {
        _RefNode = parentNode;
        _Socket = new ServerSocket(portNum, connectionBackLog, InetAddress.getByName(hostName));
        _IsRunning = true;
    }


    public void run() {
        while (_IsRunning) {
            try {
                Socket connection = _Socket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("/q")) {
                        halt();
                        break;
                    }

                    System.out.println("\r" + line);
                }

                System.out.print(">> ");

                connection.close();
            }
            catch (IOException e) {
                System.err.println("Something went wrong!");
                e.printStackTrace();
                break;
            }
        }

        // Closing the socket!
        try {
            _Socket.close();
        }
        catch (IOException e) {
            System.err.println("Failed to end connection!");
            e.printStackTrace();
        }

    }


    public void halt() {
        try {
            _RefNode.sender().sendSignal("/q");
        }
        catch (IOException e) {
            System.err.println("Something went wrong while disconnecting!");
            e.printStackTrace();
        }
        _IsRunning = false;
    }

}
