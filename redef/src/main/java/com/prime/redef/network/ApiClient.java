package com.prime.redef.network;

import androidx.annotation.NonNull;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.ContentType;

public class ApiClient {
    private final AsyncHttpClient client;
    private final String host;

    public ApiClient(String host) {
        this(host, true);
    }

    public ApiClient(String host, boolean isAsync) {
        if (isAsync)
            client = new AsyncHttpClient();
        else
            client = new SyncHttpClient();
        client.setConnectTimeout(10);
        this.host = host;
    }

    public void execute(@NonNull final ApiRequest request, final ApiRestHandler handler) {
        ResponseHandlerInterface handlerInterface = handler == null ? null : handler.getHandler();
        String url = request.getUrl();
        //RequestMethod method = request.getMethod();

        if (request instanceof GetRequest) {
            client.get(null, host + url, request.getHeaderImpls(), null, handlerInterface);
            return;
        }

        if (request instanceof PostRequest) {
            PostRequest postRequest = (PostRequest) request;

            String contentType = postRequest.getContentType();
            if (contentType == null) contentType = "application/octet-stream";

            byte[] body = postRequest.getBody();
            if (body == null) body = new byte[0];

            client.post(null,
                    host + url,
                    request.getHeaderImpls(),
                    new ByteArrayEntity(body, ContentType.parse(contentType)),
                    contentType,
                    handlerInterface);
            return;
        }

        throw new RuntimeException();
    }
}
