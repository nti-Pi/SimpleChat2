import java.io.IOException;

public class Node {

    final Receiver _Receiver;
    final Sender   _Sender;


    Node(String userName,
         String myHostName, int myPortNum, int connectionBackLog,
         String remoteHostName, int remotePortNum) throws IOException {
        _Receiver = new Receiver(this, myHostName, myPortNum, connectionBackLog);
        _Sender = new Sender(this, userName, remoteHostName, remotePortNum);
    }


    public void run() {
        Thread receiverThread = new Thread(_Receiver);
        receiverThread.start();
        _Sender.run();
        try {
            receiverThread.join();
        }
        catch (InterruptedException e) {
            System.err.println("Receiver thread was interrupted!");
            e.printStackTrace();
        }
    }


    Receiver receiver() { return _Receiver; }
    Sender   sender() { return _Sender; }

}
