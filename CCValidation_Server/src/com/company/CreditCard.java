package com.company;

enum CreditCard {
    //Every enum has a regex that defines the numbers of a valid card of it's Kind
    VISA("^4[0-9]{12}(?:[0-9]{3})?$"),
    MASTER_CARD("^5[1-5][0-9]{14}$"),
    AMERICAN_EXPRESS("^3[47][0-9]{13}$"),
    DINERS("^3(?:0[0-5]|[68][0-9])[0-9]{11}$"),
    DISCOVER("^6(?:011|5[0-9]{2})[0-9]{12}$"),
    JCB("^(?:2131|1800|35\\d{3})\\d{11}$");

    private String regex;

    CreditCard(String regex) {
        this.regex = regex;
    }

    public boolean matches(String card) {
        return card.matches(this.regex);
    }

    public static String gleanCompany(String card) {
        for (CreditCard cc : CreditCard.values()) {
            if (cc.matches(card)) {
                return String.valueOf(cc);
            }
        }
        return null;
    }
}