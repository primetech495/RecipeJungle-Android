package com.prime.redef.network;

import androidx.annotation.NonNull;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.ParseException;

class HeaderImpl implements Header {

    private String name;
    private String value;

    public HeaderImpl(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    @NonNull
    public String getName() {
        return name;
    }

    @Override
    @NonNull
    public String getValue() {
        return value;
    }

    @Override
    @NonNull
    public HeaderElement[] getElements() throws ParseException {
        return new HeaderElement[0];
    }
}
