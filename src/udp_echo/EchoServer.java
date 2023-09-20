package udp_echo;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Arrays;
public class EchoServer
{
    public static void main(String[] args) throws Exception{
        DatagramSocket serverSocket = new DatagramSocket(3000);
        DatagramPacket clientRequest = new DatagramPacket(
                new byte[1024],
                1024);
        serverSocket.receive(clientRequest);
        byte[] clientMessage = Arrays.copyOf(
                clientRequest.getData(),
                clientRequest.getLength()
        );
        System.out.println(new String(clientMessage));
        String message = (new String(clientMessage));
        byte[] reply = new byte[0];
        if (message.equals("date")) {
            LocalDateTime now = LocalDateTime.now();
            String LDT = now.toLocalDate().toString();
            reply = LDT.getBytes();
        }
        else {
            LocalDateTime now = LocalDateTime.now();
            String LTT = now.toLocalTime().toString();
            reply = LTT.getBytes();
        }

        InetAddress clientIP = clientRequest.getAddress();
        int clientPort = clientRequest.getPort();
        DatagramPacket serverReply = new DatagramPacket(
                reply,
                reply.length,
                clientIP,
                clientPort
        );
        serverSocket.send(serverReply);
        serverSocket.close();
    }
}
