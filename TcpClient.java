import java.io.*;
import java.net.Socket;

public class TcpClient extends Thread {
    public static final String ip = "127.0.0.1";
    public static final int port = 8080;

    private boolean connection_status = false;

    Socket socket;
    BufferedReader inReader;
    PrintWriter outWriter;

    @Override
    public void run() {
        super.run();
        connection_status = true;

        try {
            socket = new Socket(ip, port);

            try {
                inReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                while (connection_status) {
                    String message = inReader.readLine();
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
