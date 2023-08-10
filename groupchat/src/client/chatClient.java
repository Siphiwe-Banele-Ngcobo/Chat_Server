package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class chatClient {
    public static void main(String[] args) {
        new chatClient().run();
    }
    Scanner sc = new Scanner(System.in);
    Socket socket;
    BufferedReader in = null;
    PrintWriter out = null;

    public void run(){
        try {
            socket = new Socket("localhost", 1818);
        } catch (UnknownHostException e) {
            System.err.println("Unknown: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Unknown: localhost");
            System.exit(1);
        }
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


//            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String output = in.readLine();
            System.out.println(output);
            String name = sc.nextLine();
            out.println(name);

            Thread trd = new Thread(new ServerHandler(socket));
            trd.start();

            while (true) {
                String message = sc.nextLine();
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            out.close();
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class ServerHandler implements Runnable {
    private Socket socket;
    private BufferedReader in = null;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String message = in.readLine();
                if (message == null) {
                    return;
                }
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
                if(in != null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


