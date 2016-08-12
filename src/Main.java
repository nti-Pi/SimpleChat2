import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String userName;

        String  myHostName,
                remoteHostName;

        int     myPort,
                remotePort;

        try {
            userName = args[0];

            myHostName = args[1];
            myPort = Integer.parseInt(args[2]);

            remoteHostName = args[3];
            remotePort = Integer.parseInt(args[4]);

        } catch (IndexOutOfBoundsException e) {
            System.err.println("Incorrect usage!");
            System.err.println("java SimpleChat2 user_name my_host_name my_socket remote_host_name remote_socket");
            return;
        }

        try {
            Node node = new Node(userName, myHostName, myPort, 0, remoteHostName, remotePort);
            node.run();
        } catch (IOException e) {
            System.err.println("Failed to create the chat node!");
            e.printStackTrace();
        }
    }

}
