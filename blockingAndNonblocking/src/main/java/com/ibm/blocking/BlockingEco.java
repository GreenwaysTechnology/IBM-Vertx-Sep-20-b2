package com.ibm.blocking;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockingEco {
    public static void main(String[] args) throws Throwable {
        //this api interally communicates network interface card
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(3000));
        System.out.println("Blocking Socket : listening for new Request");
        while (true) {   // <1>
            Socket socket = server.accept();
            //each incomming request(socket request) allocate in a separate thread
            new Thread(clientHandler(socket)).start();
        }
    }

    private static Runnable clientHandler(Socket socket) {
        return () -> {

            try (
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(
                            new OutputStreamWriter(socket.getOutputStream()))) {
                String line = "";
                while (!"/quit".equals(line)) {
                    line = reader.readLine();      // <2>
                    System.out.println(Thread.currentThread().getName() + " ~ " + line);
                    writer.write(line + "\n");  // <3>
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

}
