package com.prime.redef.network;

import com.loopj.android.http.AsyncHttpResponseHandler;

public abstract class ApiRestHandler {

    private AsyncHttpResponseHandler handler;

    public ApiRestHandler() {
        handler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                Header[] h = null;
                if (headers != null) {
                    h = new Header[headers.length];
                    for (int i = 0; i < h.length; i++)
                        h[i] = new Header(headers[i].getName(), headers[i].getValue());
                }
                ApiRestHandler.this.onSuccess(statusCode, h, responseBody);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                if (headers != null) {
                    Header[] h = new Header[headers.length];
                    for (int i = 0; i < h.length; i++)
                        h[i] = new Header(headers[i].getName(), headers[i].getValue());
                    ApiRestHandler.this.onFailure(statusCode, h, responseBody, error);
                } else
                    ApiRestHandler.this.onFailure(statusCode, null, responseBody, error);
            }
        };
    }

    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

    }

    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

    }

    public final AsyncHttpResponseHandler getHandler() {
        return handler;
    }
}
