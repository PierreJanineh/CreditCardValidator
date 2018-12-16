package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadAndWrite {

    public static String readString(InputStream inputStream) throws IOException{
        int stringLength = inputStream.read();
        byte[] stringBytes = new byte[stringLength];
        int actuallyRead = inputStream.read(stringBytes);
        if (actuallyRead != stringLength)
            throw new IOException("Oops Something went wrong!");
        return new String(stringBytes);
    }

    public static void writeString(OutputStream outputStream,
                                   String string) throws IOException {
        outputStream.write(string.length());
        outputStream.write(string.getBytes());

    }

    public static void writeBoolean(OutputStream outputStream,
                                    boolean bool) throws IOException{
        if(bool == true)
            outputStream.write(1);
        else
            outputStream.write(0);
    }

    public static boolean readBoolean(InputStream inputStream) throws IOException{
        int bool = inputStream.read();
        if(bool == 0)
            return false;
        else
            return true;
    }

    public static String readBytesFromFile(String filePath){
        String content = "";
        try{
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        }
        catch (IOException e){
            e.printStackTrace();
        }return content;
    }
}