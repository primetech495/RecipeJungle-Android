package com.prime.redef.network;

public class Header {
    private String name;
    private String value;

    // TODO: elements are not implemented yet: see Header for asyncHttp library

    public Header(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
