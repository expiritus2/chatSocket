import java.io.*;
import java.net.Socket;

public class ConversationHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String name;
    private PrintWriter pw;
    public static FileWriter fw;
    public static BufferedWriter bw;

    public ConversationHandler(Socket socket) throws IOException {
        this.socket = socket;
        fw = new FileWriter("/home/ubuntu/chatServer.txt", true);
        bw = new BufferedWriter(fw);
        pw = new PrintWriter(bw, true);
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            int count = 0;
            while (true){
                if(count > 0){
                    out.println("NAMEALREADYEXISTS");
                } else {
                    out.println("NAMEREQUIRED");
                }

                name = in.readLine();

                if(name == null){
                    return;
                }

                if(!Server.userName.contains(name)){
                    Server.userName.add(name);
                    break;
                }

                count++;
            }

            out.println("NAMEACCEPTED"+name);
            Server.printWriters.add(out);

            while (true) {
                String message = in.readLine();
                if(message == null){
                    return;
                }

                pw.println(name + ": " + message);

                for(PrintWriter writer : Server.printWriters){
                    writer.println(name + ": " + message);
                    System.out.println(Server.printWriters.size());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
