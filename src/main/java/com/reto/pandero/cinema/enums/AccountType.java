package com.reto.pandero.cinema.enums;

public enum AccountType {

    DEFAULT(0),
    BASIC(1),
    STANDARD(2),
    PREMIUM(3);
    private final int value;

    private AccountType(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
