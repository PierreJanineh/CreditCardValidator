package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CCValidation implements Writeable {
    private boolean valid;
    private String cardType;
    private int error;
    private String cardNo;

    //Constructor for Invalid Cards
    public CCValidation(String cardNo,
                        int error) {
        this.cardNo = cardNo;
        this.error = error;
        this.valid = false;
    }

    //Constructor for Valid Cards
    public CCValidation(String cardNo,
                        String cardType) {
        this.cardNo = cardNo;
        this.valid = true;
        this.cardType = cardType;
    }

    public CCValidation(InputStream inputStream) throws IOException {
        read(inputStream);
    }

    public CCValidation(String cardNo){
        this.cardNo = cardNo;
    }

    boolean isValid() {
        return valid;
    }
    String getCardType() {
        return cardType;
    }
    public int getError() {
        return error;
    }
    public String cardNo() {
        return this.cardNo;
    }
    String getMessage() {
        return ((!valid) ? String.valueOf(error) : cardType) ;
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        ReadAndWrite.writeString(outputStream,cardNo);
        ReadAndWrite.writeBoolean(outputStream,valid);
        if(valid)
            ReadAndWrite.writeString(outputStream,cardType);
        else
            outputStream.write(error);
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        cardNo = ReadAndWrite.readString(inputStream);
        valid = ReadAndWrite.readBoolean(inputStream);
        if(valid)
            cardType = ReadAndWrite.readString(inputStream);
        else
            error = inputStream.read();
    }

    @Override
    public String toString() {
        return "Card Number: " + cardNo + "\n" +
                "\tCardType: " + cardType + "\n" +
                "\tError: " + error + "\n" +
                "___________________\n\n";
    }
}