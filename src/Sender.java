import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Sender {

    final Node    _RefNode;
    final String  _UserName;
    final String  _HostName;
    final int     _PortNumber;

    Sender(Node refNode, String userName, String hostName, int portNum) {
        _RefNode = refNode;
        _UserName = userName;
        _HostName = hostName;
        _PortNumber = portNum;
    }


    public void sendMessage(String message) throws IOException {
        Socket connectionSocket = new Socket(_HostName, _PortNumber);
        PrintWriter connectionWriter = new PrintWriter(connectionSocket.getOutputStream());

        connectionWriter.printf("\n[%s says: ] ", _UserName);
        connectionWriter.write(message.toCharArray());
        // connectionWriter.print('\n');
        connectionWriter.flush();

        connectionSocket.close();
    }


    public void sendSignal(String signal) throws IOException {
        Socket connectionSocket = new Socket(_HostName, _PortNumber);
        PrintWriter connectionWriter = new PrintWriter(connectionSocket.getOutputStream());

        connectionWriter.write(signal.toCharArray());
        // connectionWriter.print('\n');
        connectionWriter.flush();

        connectionSocket.close();
    }


    private static void printHelp() {
        System.out.println("'/h':   Prints your help.");
        System.out.println("'/me':  Prints your user-name.");
        System.out.println("'/q':   Quits the chat.");
    }


    void printUserName() {
        System.out.printf("Your user name is: '%s'\n", _UserName);
    }


    void quit() {
        try { sendSignal("/q"); } catch (IOException e) { return; }
        // _RefNode.receiver().halt();
    }


    void run() {
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        boolean isRunning = true;

        System.out.println("Enter '/h' for help. '/q' quits the chat.");

        while (isRunning) {
            try {
                System.out.print(">> ");
                String input = consoleInput.readLine();

                if (input.startsWith("/")) {
                    // Command!
                    if (input.startsWith("/h")) {
                        // Print the help:
                        printHelp();
                    }
                    else if (input.startsWith("/q")) {
                        quit();
                        isRunning = false;
                    }
                    else if (input.startsWith("/me")) {
                        printUserName();
                    }
                }
                else {
                    sendMessage(input);
                }
            }
            catch (IOException e) {
                System.err.println("Connection failed!");
                e.printStackTrace();
            }
        }
    }

}
