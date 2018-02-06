import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

public class TcpServer extends Thread {
    private ServerSocket serverSocket;
    private Socket socket;

    private int port;
    public boolean connection_status = false;

    private BufferedReader inReader;
    private PrintWriter outWriter;

    private String message;

    public TcpServer(final int port) {
        this.port = port;
    }

    public TcpServer() {
        if ((port = getAvailablePort()) == -1)
            System.out.println("No available ports!!!!!!!!");
        else
            this.start();
    }

    private int getAvailablePort() {
        ServerSocket server;

        for (int port = 1000; port < 1024; port++) {
            try {
                server = new ServerSocket(port);
                server.close();
                return port;
            } catch (IOException ex) {
                System.err.println("no available ports");
            }
        }

        return -1;
    }

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

                while (connection_status) 
                    message = inReader.readLine();

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

    public String getMessage() {
        if(message != null) 
            return message;
        return null;
    }

    public void setMessage(final String data) {
        if (outWriter != null && !outWriter.checkError()) {
            outWriter.println(data);
            outWriter.flush();
        }
    }
}
