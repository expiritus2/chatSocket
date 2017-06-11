import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static ArrayList<String> userName = new ArrayList<>();
    public static ArrayList<PrintWriter> printWriters = new ArrayList<>();

    public static void main(String[] args) {
        try {
            System.out.println("Waiting for client...");
            ServerSocket ss = new ServerSocket(9806);
            while (true) {
                Socket soc = ss.accept();
                System.out.println("Connection established");
                ConversationHandler handler = new ConversationHandler(soc);
                handler.start();
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
