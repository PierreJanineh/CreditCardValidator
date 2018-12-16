package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.System.*;

public class Main {
    public static final int FAILED_NUMERIC_CHECK = 100;
    public static final int FAILED_LENGTH_CHECK = 200;
    public static final int FAILED_LUHN_CHECK = 150;
    public static final int FAILED_CARD_COMPANY_CHECK = 50;
    public static final String CLIENT_FILE = "Credit_Card.txt";
    public static final String VALID_CARDS_FILE = "Valid_Credit_Cards.txt";
    public static List<String> creditCards = CCValidation.creditCards;

    public static void main(String[] args) {
        int firstTime = 1;//a helper variable for showing the Welcome message
        printToScreen(firstTime);
    }
    //Main UI function -- Displays Main Menu and functions, goes back to the Main Menu whenever a function finishes and ends when the user types "exit"
    public static String printToScreen(int firstTime){
        out.println("-------------- VALI.CARD --------------");
        //Do not display this line unless if it's the first time in a session
        if(firstTime == 1) {
            out.println("WELCOME to\tVALI CARD\tValidator\n" +
                    "This machine can check Credit Cards validity through checking it's Luhn algorithm and multiple other functions.\n" +
                    "The results expected in this check are:\n" +
                    "\tA. VALID\t-->\tCard Issuer\n" +
                    "\tB. INVALID\t-->\tError\n--------\n" +
                    "Feel free to use this sample to test the app >>>\n\t4444444444444448-5500005555555559-371449635398431-6011016011016011-3566003566003566-1111111111111111-abcdabcdabcdabcd-123");
        }
        firstTime = 2;//chenge after first time
        out.println("\n-------- Choose your operation --------\n" +
                "1.Validator\t\t\t2.View History\n" +
                "3.Export History\t4.Clear History\n" +
                "either type\t\t' back ' to go back to Previous Menu\n" +
                "\tor type\t\t' exit ' to EXIT");
        String userString = readStringFromConsole();
        //Unless the user types "exit" switch between requested functions
        if (!userString.equals("exit")){
            switch(userString){
                case "1":
                    //Credit Card Validator
                    out.println("\n\n---------- VALIDATOR MACHINE ---------\n" +
                            "Type in Credit Card numbers to check (Seperated by ' - ' if more than one).\n" +
                            "or type\t\t' back ' to go back to the Previous Menu");
                    checkCC();
                    break;
                case "2":
                    //View All History
                    out.println("\n\n-------------- HISTORY --------------");
                    checkIfAnyHistory();
                    break;
                case "3":
                    //Export History
                    out.println("\n\n---------- EXPORT HISTORY ----------\n" +
                            "-------- Choose your operation -------\n" +
                            "1. Export Valid Cards ONLY\n" +
                            "2. Export ALL Cards ( Valid & Invalid )\n" +
                            "or type\t\t' back ' to go back to the Previous Menu");
                    String userOp = readStringFromConsole();
                    if (!userOp.contains("back")) {

                        switch (userOp) {
                            case "1":
                                //Export Only valid cards
                                out.println("You're default file path is the Project's Directory \n" +
                                        "If you wish to change it press 1, or 2 to keep it");
                                String userRe = readStringFromConsole();
                                    switch(userRe) {
                                        case "1":
                                            //Change directory
                                            out.println("Please type in the File Directory only in the following form:\n" +
                                                    "/User/Folder/Folder/");
                                            String filePathEnt = readStringFromConsole();
                                            exportValidHistory(filePathEnt + VALID_CARDS_FILE);
                                            out.println("File has been Exported!");
                                            break;
                                        case "2":
                                            //Keep directory
                                            exportValidHistory(VALID_CARDS_FILE);
                                            out.println("File has been Exported!");
                                            break;
                                    }
                                break;
                            case "2":
                                //Export all cards
                                out.println("You're default file path is the Project's Directory \n" +
                                        "If you wish to change it press 1, or 2 to keep it\n");
                                String userEnt = readStringFromConsole();
                                switch (userEnt) {
                                    case "1":
                                        //Change directory
                                        out.println("Please type in the File Directory only in the following form:\n" +
                                                "/User/Folder/Folder/");
                                        String filePathEnt = readStringFromConsole();
                                        exportAll(filePathEnt + CLIENT_FILE);
                                        out.println("File has been Exported!");
                                        break;
                                    case "2":
                                        //Keep directory
                                        exportAll(CLIENT_FILE);
                                        out.println("File has been Exported!");
                                        break;
                                }
                                break;
                        }
                    }break;
                case "4":
                    //Clear History
                    out.println("\n\n------------ CLEAR HISTORY ------------\n" +
                            "------------ Are you sure? -----------\n" +
                            "1.Yes\t\t2.No\n" +
                            "or type\t\t' back ' to go back to the Previous Menu");
                    String userEn = readStringFromConsole();
                        switch (userEn) {
                            case "1":
                                //YES > Clear history
                                clearHistory();
                                out.println("History Cleared!");
                                break;
                            case "2":
                                //NO > Go back to main menu
                                break;
                        }break;
            }
            return printToScreen(firstTime);
        }return null;
    }

    //Helper function to read input
    private static String readStringFromConsole(){
        Scanner s = new Scanner(in);
        return s.nextLine();
    }

    //Check Credit Card numbers then call the function in the server to apply functions and get results
    public static void checkCC() {
        String cardNo = readStringFromConsole();
        while (!cardNo.contains("back")) {
            //Check if there is more than one number to split them and save each one of them to the list and perform the function
            if (cardNo.contains("-")) {
                String[] split = cardNo.split("-");
                for (int i = 0; i < split.length; i++) {
                    creditCards.add(split[i]);
                    Server.checkCC(split[i], new Server.ObjectAnswerListener<CCValidation>() {
                        @Override
                        public void onAnswerReady(CCValidation ccValidation) {
                            printMessages(ccValidation);
                        }
                    });
                }
                checkCC();
            } else if (cardNo.isEmpty()) {//Alert the client if they typed enter with nothing
                out.println("\nNothing was typed in..\nRemember you can type 'back' to go back to main menu");
                checkCC();
            } else {
                creditCards.add(cardNo);
                Server.checkCC(cardNo, new Server.ObjectAnswerListener<CCValidation>() {
                    @Override
                    public void onAnswerReady(CCValidation ccValidation) {
                        printMessages(ccValidation);
                    }
                });
                checkCC();
            }
        }printToScreen(2);
    }

    //Send a function to the server to clear any history
    public static void clearHistory(){
        Server.clearHistory(new Server.ObjectAnswerListener<String>(){
            @Override
            public void onAnswerReady(String string){
                if(!string.contains("0")){
                    out.println("History Cleared!");
                }else{
                    out.println("Failed to clear History");
                }
            }
        });
    }

    //Get history from the server and send it to the function that writes it to a file
    public static void copyData(String filePath) {
        Server.copyData(new Server.ObjectAnswerListener<CCValidation>() {
            @Override
            public void onAnswerReady(CCValidation ccValidation) {
                try {
                    createFileAndAddCards(ccValidation, filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Get history from the server and send it to the function that writes it to a file
    public static void copyValidData(String filePath) {
        Server.copyValidData(new Server.ObjectAnswerListener<CCValidation>() {
            @Override
            public void onAnswerReady(CCValidation ccValidation) {
                try {
                    createFileAndAddCards_Valid(ccValidation, filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Create file in specified directory and write data
    private static void createFileAndAddCards(CCValidation ccValidation, String filePath) throws IOException {
        File file = new File(filePath);
        if(file.createNewFile())
            out.println("a new File in the Directory set has been created successfully!");
        FileWriter fileWriter = new FileWriter(file,true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("");
        bufferedWriter.append(ccValidation.cardNo()+" >\n");
        if(ccValidation.isValid()){
            bufferedWriter.append("\tIssuer: " + ccValidation.getCardType() + "\n" +
                    "------------------\n");
        }else{
            int message = ccValidation.getError();
            String message1="";
            switch (message) {
                case FAILED_CARD_COMPANY_CHECK:
                    message1 = "Failed Card Company check";
                    break;
                case FAILED_LENGTH_CHECK:
                    message1 = "Failed Length check";
                    break;
                case FAILED_LUHN_CHECK:
                    message1 = "Failed Luhn check";
                    break;
                case FAILED_NUMERIC_CHECK:
                    message1 = "Failed Numeric Check";
                    break;
            } bufferedWriter.append("\tError:" + message1 + "\n" +
                    "------------------\n");
        }
        bufferedWriter.close();
        fileWriter.close();
    }

    //Create file in specified directory and write data
    private static void createFileAndAddCards_Valid(CCValidation ccValidation, String filePath) throws IOException {
        File file = new File(filePath);
        if(file.createNewFile())
            out.println("a new File in the Directory set has been created successfully!");
        FileWriter fileWriter = new FileWriter(file,true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("");
        if(ccValidation.isValid()){
            bufferedWriter.append(ccValidation.cardNo()+" >\n");
            bufferedWriter.append("\tIssuer: " + ccValidation.getCardType() + "\n" +
                    "------------------\n");
        }
        bufferedWriter.close();
        fileWriter.close();
    }

    //Get the data from the server and export to the file
    public static void exportAll(String filePath) {
        Server.exportAll(new Server.ObjectAnswerListener<String>() {
            @Override
            public void onAnswerReady(String string) {
                //Announce if no history found
                if(string.contains("0")){
                    out.println("No History to Load..\n");
                }else {
                    copyData(filePath);
                }
            }
        });
    }

    //Get the data from the server and export to the file
    public static void exportValidHistory(String filePath) {
        Server.exportValidHistory(new Server.ObjectAnswerListener<String>() {
            @Override
            public void onAnswerReady(String string) {
                //Announce if no history found
                if(string.contains("0")){
                    out.println("No History to Load..\n");
                }else {
                    copyValidData(filePath);
                }
            }
        });
    }

    //Print History to the Screen
    public static void viewHistory() {
        Server.viewHistory(new Server.ObjectAnswerListener<CCValidation>() {
            @Override
            public void onAnswerReady(CCValidation ccValidation) {
                printMessages(ccValidation);
            }
        });
    }

    //Check if there's any history on the server and let the client know
    public static void checkIfAnyHistory() {
        Server.checkIfAnyHistory(new Server.ObjectAnswerListener<String>() {
            @Override
            public void onAnswerReady(String string) {
                //Announce if no history found
                if(string.contains("0")){
                    out.println("No History to Load..\n");
                }else {
                    viewHistory();
                }
            }
        });
    }

    //Convert all Errors and Card Types to String
    private static void printMessages(CCValidation ccValidation) {
        String message = "";
        String cardNo = ccValidation.cardNo();
        if (ccValidation.isValid()) {
            message = ccValidation.getMessage();
            switch (message) {
                case "AMERICAN_EXPRESS":
                    message = "American Express";
                    break;
                case "JCB":
                    message = "JCB";
                    break;
                case "DISCOVER":
                    message = "Discover";
                    break;
                case "DINERS":
                    message = "Diners";
                    break;
                case "MASTER_CARD":
                    message = "Master Card";
                    break;
                case "VISA":
                    message = "Visa";
                    break;
            }
            out.println("_______________\nThis Credit Card number ( " + cardNo + " ) is Valid\n\tand it's issuer is: " + message);
        } else {
            int error = ccValidation.getError();
            switch (error) {
                case FAILED_NUMERIC_CHECK:
                    message = "Failed Numeric check";
                    break;
                case FAILED_LENGTH_CHECK:
                    message = "Failed Length check";
                    break;
                case FAILED_LUHN_CHECK:
                    message = "Failed Luhn check";
                    break;
                case FAILED_CARD_COMPANY_CHECK:
                    message = "Failed card Company check";
                    break;
            }
            out.println("_______________\nThis Credit Card number ( " + cardNo + " ) is Invalid\n\tand the error was: " + message);
        }
    }

}