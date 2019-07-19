package com.prime.redef.network;

public class GetRequest extends ApiRequest {

    public GetRequest(String url) {
        super(url, RequestMethod.GET);
    }
}
