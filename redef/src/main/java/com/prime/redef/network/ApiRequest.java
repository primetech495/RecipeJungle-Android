package com.prime.redef.network;

import java.util.HashMap;
import java.util.Map;

public abstract class ApiRequest {
    private String url;
    private RequestMethod method;
    protected final HashMap<String, String> headers;

    public ApiRequest(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
        this.headers = new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void putHeader(String header, String value) {
        headers.put(header, value);
    }

    HeaderImpl[] getHeaderImpls() {
        int c = 0;
        HeaderImpl[] res = new HeaderImpl[headers.size()];
        for (Map.Entry<String, String> e : headers.entrySet())
            res[c++] = new HeaderImpl(e.getKey(), e.getValue());
        return res;
    }
}
