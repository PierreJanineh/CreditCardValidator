package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CCValidation implements Writeable {
    private boolean valid;
    private String cardType;
    private int error;
    private String cardNo;
    public static List<String> creditCards = new List<String>() {
        @Override
        public int size() {
            return this.size();
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(String s) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public String get(int index) {
            return null;
        }

        @Override
        public String set(int index, String element) {
            return null;
        }

        @Override
        public void add(int index, String element) {

        }

        @Override
        public String remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<String> listIterator() {
            return null;
        }

        @Override
        public ListIterator<String> listIterator(int index) {
            return null;
        }

        @Override
        public List<String> subList(int fromIndex, int toIndex) {
            return null;
        }
    };

    //Constructor for Invalid Cards
    public CCValidation(String cardNo,
                        int error) {
        this.error = error;
        this.valid = false;
        this.cardNo = cardNo;
    }

    //Constructor for Valid Cards
    public CCValidation(String cardNo,
                        String cardType) {
        this.cardNo = cardNo;
        this.valid = true;
        this.cardType = cardType;
    }

    public CCValidation(String cardNo){
        this.cardNo = cardNo;
    }

    public CCValidation(CCValidation ccValidation){
        this(ccValidation.cardNo, ccValidation.getCardType());
    }

    public CCValidation(InputStream inputStream) throws IOException {
        read(inputStream);
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
        if (valid) {
            cardType = ReadAndWrite.readString(inputStream);
        } else {
            error = inputStream.read();
        }
    }

    @Override
    public String toString() {
        return "Card Number: " + cardNo + "\n" +
                "\tCardType: " + cardType + "\n" +
                "\tError: " + error + "\n" +
                "___________________\n\n";
    }
}
