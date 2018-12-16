package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//Makes the object that implements it capable of being written and read using IO
public interface Writeable {

    void write(OutputStream outputStream) throws IOException;
    void read(InputStream inputStream) throws IOException;

}