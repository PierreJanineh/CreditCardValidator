package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Server {

    public static final int CHECK_CC = 10;
    public static final int HISTORY = 11;
    public static final int CHECK_IF_ANY = 12;
    public static final int CLEAR_HISTORY = 13;
    public static final int REMOVE_SESSION_ITEMS = 20;

    public static final String HOST = "127.0.0.1";
    public static final int PORT = 3000;

    public static int creditCardsNum;

    //Check Credit Card function >> gets the Card number and sends it to the server through the 'connect' method and call the listener to show UI data
    public static void checkCC(String cardNo,
                               ObjectAnswerListener listener) {
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream,
                                  OutputStream outputStream) throws IOException {
                outputStream.write(CHECK_CC);
                ReadAndWrite.writeString(outputStream, cardNo);
                CCValidation ccValidation = new CCValidation(inputStream);
                listener.onAnswerReady(ccValidation);
            }
        });
    }

    //Clears History saved on the server
    public static void clearHistory(final ObjectAnswerListener listener) {
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(CLEAR_HISTORY);
                String operation = ReadAndWrite.readString(inputStream);
                listener.onAnswerReady(operation);
            }
        });
    }

    //Exporting Only valid cards from History on server
    public static void exportValidHistory(final ObjectAnswerListener listener){
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                    outputStream.write(CHECK_IF_ANY);
                    creditCardsNum = inputStream.read();
                    String creditCardsNumStr = String.valueOf(creditCardsNum);
                    listener.onAnswerReady(creditCardsNumStr);
                }
        });
    }

    //Check if there's any History in order to View History
    public static void checkIfAnyHistory(final ObjectAnswerListener listener) {
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(CHECK_IF_ANY);
                creditCardsNum = inputStream.read();
                String creditCardsNumStr = String.valueOf(creditCardsNum);
                listener.onAnswerReady(creditCardsNumStr);
            }
        });
    }

    //Send data to listener in order to add them to file one by one
    public static void copyData(final ObjectAnswerListener listener) {
        connect(new ConnectionListener() {

            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(HISTORY);
                String oneCC = "";
                if(creditCardsNum > 1) {
                    for (int i = 0; i < creditCardsNum; i++) {
                        CCValidation ccValidation = new CCValidation(inputStream);
                        listener.onAnswerReady(ccValidation);
                    }
                }else{
                    CCValidation ccValidation = new CCValidation(inputStream);
                    listener.onAnswerReady(ccValidation);
                }
            }
        });
    }

    //Send data to listener in order to add them to file one by one
    public static void copyValidData(final ObjectAnswerListener listener) {
        connect(new ConnectionListener() {

            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(HISTORY);
                String oneCC = "";
                if(creditCardsNum > 1) {
                    for (int i = 0; i < creditCardsNum; i++) {
                        CCValidation ccValidation = new CCValidation(inputStream);
                        listener.onAnswerReady(ccValidation);
                    }
                }else{
                    CCValidation ccValidation = new CCValidation(inputStream);
                    listener.onAnswerReady(ccValidation);
                }
            }
        });
    }


    //Check if there's any History in order to export
    public static void exportAll(final ObjectAnswerListener listener){
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(CHECK_IF_ANY);
                creditCardsNum = inputStream.read();
                String creditCardsNumStr = String.valueOf(creditCardsNum);
                listener.onAnswerReady(creditCardsNumStr);
            }
        });
    }

    //get data to sent to listener to show History's UI
    public static void viewHistory(final ObjectAnswerListener listener){
        connect(new ConnectionListener() {
            @Override
            public void onConnect(InputStream inputStream,
                                  OutputStream outputStream) throws IOException {
                outputStream.write(HISTORY);
                String oneCC = "";
                if(creditCardsNum > 1) {
                    for (int i = 0; i < creditCardsNum; i++) {
                        CCValidation ccValidation = new CCValidation(inputStream);
                        listener.onAnswerReady(ccValidation);
                    }
                }else{
                    CCValidation ccValidation = new CCValidation(inputStream);
                    listener.onAnswerReady(ccValidation);
                }
            }
        });
    }

    //Connect method to connect to the server
    public static void connect(ConnectionListener listener){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        Socket socket = null;
        try{
            socket = new Socket(HOST, PORT);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            listener.onConnect(inputStream,outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface ConnectionListener{
        void onConnect(InputStream inputStream,
                       OutputStream outputStream) throws IOException;
    }

    //On data ready function to call whenever the server needs to display data to the UI
    public interface ObjectAnswerListener<T>{
        void onAnswerReady(T object);
    }
}
