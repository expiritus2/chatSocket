import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static JFrame chatWindow = new JFrame("Chat Application");
    public static JTextArea chatArea = new JTextArea(22, 40);
    public static JTextField textField = new JTextField(40);
    public static JLabel blankLabel = new JLabel("              ");
    public static JButton sendButton = new JButton("Send");
    public static BufferedReader in;
    public static PrintWriter out;
    public static JLabel nameLabel = new JLabel("             ");

    public Client() {
        chatWindow.setLayout(new FlowLayout());
        chatWindow.add(nameLabel);
        chatWindow.add(new JScrollPane(chatArea));
        chatWindow.add(blankLabel);
        chatWindow.add(textField);
        chatWindow.add(sendButton);

        chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatWindow.setSize(475, 500);
        chatWindow.setVisible(true);

        textField.setEnabled(false);
        chatArea.setEnabled(false);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                out.println(Client.textField.getText());
                textField.setText("");
            }
        });
    }

    public void startChat() throws Exception{
        String ipAddress = JOptionPane.showInputDialog(
            chatWindow,
            "Enter IP Address:",
            "IP Address Required!!",
             JOptionPane.PLAIN_MESSAGE
        );

        Socket soc = new Socket(ipAddress, 9806);
        in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        out = new PrintWriter(soc.getOutputStream(), true);

        while (true) {
            String str = in.readLine();
            if(str.equals("NAMEREQUIRED")){
                String name = JOptionPane.showInputDialog(
                    chatWindow,
                        "Enter a unique name:",
                        "Name Required!!",
                        JOptionPane.PLAIN_MESSAGE
                );
                out.println(name);
            } else if (str.equals("NAMEALREADYEXISTS")){
                String name = JOptionPane.showInputDialog(
                        chatWindow,
                        "Enter another name:",
                        "Name Already Exists!!",
                        JOptionPane.WARNING_MESSAGE
                );

                out.println(name);
            } else if (str.startsWith("NAMEACCEPTED")){
                textField.setEnabled(true);
                nameLabel.setText("You are logged in as: " + str.substring(12));
            } else {
                chatArea.append(str + "\n");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.startChat();
    }
}
