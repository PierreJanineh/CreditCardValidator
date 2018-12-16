package com.company;

import java.io.*;
import java.nio.ByteBuffer;

public class ReadAndWrite {


    static String readString(InputStream inputStream) throws IOException{
        int stringLength = inputStream.read();
        byte[] stringBytes = new byte[stringLength];
        int actuallyRead = inputStream.read(stringBytes);
        if (actuallyRead != stringLength)
            throw new IOException("Oops Something went wrong!");
        return new String(stringBytes);
    }

    static void writeString(OutputStream outputStream,
                            String string) throws IOException {
        byte[] bytesBuffer = string.getBytes();
        outputStream.write(bytesBuffer.length);
        outputStream.write(bytesBuffer);

    }

    static void writeBoolean(OutputStream outputStream,
                             boolean bool) throws IOException{
        if(bool)
            outputStream.write(1);
        else
            outputStream.write(0);
    }

    static boolean readBoolean(InputStream inputStream) throws IOException{
        int bool = inputStream.read();
        if(bool == 0)
            return false;
        else
            return true;

    }


}