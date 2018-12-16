package com.company;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientThread extends Thread {
    public static final int CHECK_CC = 10;
    public static final int HISTORY = 11;
    public static final int CHECK_IF_ANY = 12;
    public static final int CLEAR_HISTORY = 13;
    public static final int FAILED_NUMERIC_CHECK = 100;
    public static final int FAILED_LENGTH_CHECK = 200;
    public static final int FAILED_LUHN_CHECK = 150;
    public static final int FAILED_CARD_COMPANY_CHECK = 50;
    public static final String pathName = "src/com/company/";
    public static final String CREDIT_CARDS_TXT = "creditCards.txt";

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Map<String, CCValidation> creditCardsMap;
    public static List<String> creditCards = new ArrayList<>();



    public ClientThread(Socket socket,
                        Map<String, CCValidation> creditCardsMap) {
        this.socket = socket;
        this.creditCardsMap = creditCardsMap;
    }

    @Override
    public void run() {
        try{
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            int action = inputStream.read();
            switch (action){
                case CHECK_CC:
                    checkCC();
                    saveDataToServer();
                    break;
                case HISTORY:
                    viewHistory();
                    break;
                case CHECK_IF_ANY:
                    checkIfAny();
                    break;
                case CLEAR_HISTORY:
                    clearHistory();
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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

    private void checkCC() throws IOException {
        String cardNo = ReadAndWrite.readString(inputStream);
        creditCards.add(cardNo);
        CCValidation ccValidation = isValid(cardNo);
        ccValidation.write(outputStream);
    }

    private void saveDataToServer() throws IOException {
//        ReadAndWrite.writeCreditCardsToFile(creditCards,new File (pathName + "creditCards.txt"));
        FileWriter writer = new FileWriter(pathName + CREDIT_CARDS_TXT);
        for(String string:creditCards){
            writer.write(string + ";;");
        }
        writer.close();
    }

    private void clearHistory() throws IOException {
        FileWriter writer = new FileWriter(pathName + CREDIT_CARDS_TXT);
        writer.write("");
        writer.close();
        ReadAndWrite.writeString(outputStream,"1");
    }

    private void checkIfAny() throws IOException {
        String ccFile = ReadAndWrite.readBytesFromFile(pathName+CREDIT_CARDS_TXT);
        System.out.println(ccFile.length());
        creditCards.clear();
        if(ccFile.contains(";;")){
            String[] split = ccFile.split(";;");
            for (int i = 0; i < split.length; i++) {
                creditCards.add(split[i]);
            }
            outputStream.write(split.length);
        }else if(ccFile.isEmpty()){
            outputStream.write(0);
        }else{
            outputStream.write(1);
            creditCards.add(ccFile);
        }
    }

    private void viewHistory() throws IOException {
        int creditCardsNum = creditCards.size();
        if(creditCardsNum > 1) {
            for (int i = 0; i < creditCardsNum; i++) {
                CCValidation ccValidation = isValid(creditCards.get(i));
                ccValidation.write(outputStream);
            }
        } else {
            CCValidation ccValidation = isValid(creditCards.toString());
            ccValidation.write(outputStream);
        }
    }

    private static void exportAll(){

    }

    private static CCValidation isValid(final String cardNo) throws IOException {

        // Numeric Check
        if (!numericCheck(cardNo)){
            return new CCValidation(cardNo, FAILED_NUMERIC_CHECK);
        }

        // Remove all non-numerics
        String card = cardNo.replaceAll("[^0-9]+", "");

        // Length Check
        if (card.length() < 13 || card.length() > 19) {
            return new CCValidation(card, FAILED_LENGTH_CHECK);
        }

        // Luhn Check
        if (!luhnCheck(card)) {
            return new CCValidation(card, FAILED_LUHN_CHECK);
        }

        // Card Issuer Check
        String cc = CreditCard.gleanCompany(card);
        if (cc == null) {
            return new CCValidation(card, FAILED_CARD_COMPANY_CHECK);
        }

        return new CCValidation(card, cc);
    }

    // Numeric Check function
    private static boolean numericCheck(String cardIn) {
        int digits = cardIn.length();
        long sum = 0;
        for (int count = 0; count < digits; count++) {
            int digit = 0;
            try {
                digit = Integer.parseInt(cardIn.charAt(count) + "");
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    //Luhn check for credit card numbers validation
    private static boolean luhnCheck(String cardNumber) {
        // 0-9 Validation
        int digits = cardNumber.length();
        int oddOrEven = digits & 1;
        long sum = 0;
        for (int count = 0; count < digits; count++) {
            int digit = 0;
            try {
                digit = Integer.parseInt(cardNumber.charAt(count) + "");
            } catch (NumberFormatException e) {
                return false;
            }

            if (((count & 1) ^ oddOrEven) == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }

        return (sum != 0) && (sum % 10 == 0);
    }

}
