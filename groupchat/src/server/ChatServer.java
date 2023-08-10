package server;

import daos.MessageDAOimpl;
import model.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    public static ArrayList<PrintWriter> client = new ArrayList<>();
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out = null;

    public static void main(String[] args) {
        new ChatServer().run();
    }

    public void run(){
        try {
            serverSocket = new ServerSocket(1818);
        } catch (IOException e) {
            System.err.println("Could not listen on port 1818");
            System.exit(1);
        }
            while (true) {
                try{
                System.out.println("Listening on port : 1818");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
            System.err.println("Failed to accept...");
            System.exit(1);
        }
                System.out.println("Connecting to IP address:" + clientSocket.getInetAddress().getHostAddress());
                try {
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    client.add(out);
                    Thread trd = new Thread(new ClientHandler(clientSocket, out));
                    trd.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }

            }

    public static void broadcastArrivalANDExit(String name, PrintWriter sender) {
        for (PrintWriter client : client) {
            if (!client.equals(sender)) {
                client.println(name);
            }
        }
    }

    public static void broadcastMessage(Message message, PrintWriter sender) {

        for (PrintWriter client : client) {
            if (!client.equals(sender)) {
                client.println(message);
            }
        }
    }
}
class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private MessageDAOimpl messageDAOimpl = new MessageDAOimpl();

    public ClientHandler(Socket socket, PrintWriter out) {
        this.clientSocket = socket;
        this.out = out;
    }

    public void run() {
        try {
//            out = new PrintWriter(clientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("Please enter your username:");
            String name = in.readLine();
            System.out.println(name + " connected!");
//            out.println("Enter message:");
            ChatServer.broadcastArrivalANDExit(name + " has joined the chat!/\n type '/quit' to leave chat.",out);
            while (true) {
                String message = in.readLine();
                if (message.startsWith("/quit")) {
                    ChatServer.broadcastArrivalANDExit(name + " has left the chat!",out);
                    shutdown();

                }
                Message messageSent = new Message(name,message);
                messageDAOimpl.saveMessage(messageSent);
                ChatServer.broadcastMessage(messageDAOimpl.displayMessage(messageSent), out);
            }
        } catch (IOException e) {
            shutdown();
        }
    }
    public void shutdown(){
        if (out != null) {
            ChatServer.client.remove(out);
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            messageDAOimpl.closeConnection();
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
