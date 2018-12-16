package com.company;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final int PORT = 3000;

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try{
            String pathName = "src/com/company/";
            serverSocket = new ServerSocket(PORT);
            File creditCardsFile = new File(pathName + "com/company/creditCards.txt");
            Map<String, CCValidation> creditCardsMap = new HashMap<>();
            while (true){
                Socket socket = serverSocket.accept();
                new ClientThread(socket, creditCardsMap).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
