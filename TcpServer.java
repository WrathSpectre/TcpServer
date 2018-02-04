import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

public class TcpServer extends Thread {
    public static final int port = 8080;
    private boolean connection_status = false;

    ServerSocket serverSocket;
    Socket socket;

    BufferedReader inReader;
    PrintWriter outWriter;

    @Override
    public void run() {
        super.run();
        connection_status = true;

        try {
            System.out.println("Server: Connecting...");

            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();

            System.out.println("Server: Receiving...");

            try {
                inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                while (connection_status) {
                   String message = inReader.readLine();
                   System.out.println(message);
                }

            } catch (Exception e) {
                System.out.println("Server: Error");
                e.printStackTrace();
            } finally {
                socket.close();
                System.out.println("Server: Done.");
            }

        } catch (Exception e) {
            System.out.println("Server: Error");
            e.printStackTrace();
        }
    }

    public void send(final String data) {
        if (outWriter != null && !outWriter.checkError()) {
            outWriter.println(data);
            outWriter.flush();
        }
    }
}
