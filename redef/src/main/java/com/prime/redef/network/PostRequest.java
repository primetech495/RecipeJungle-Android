package com.prime.redef.network;

import androidx.annotation.NonNull;

import com.prime.redef.json.JObject;
import com.prime.redef.utils.ObjectUtils;

public class PostRequest extends ApiRequest {

    private byte[] body = new byte[0];
    private String contentType = "application/octet-stream";

    public PostRequest(@NonNull String url) {
        super(url, RequestMethod.POST);
    }

    public final void setJsonBody(@NonNull String json) {
        setBody(ObjectUtils.utf8Bytes(json));
        setContentType("application/json");
    }

    public final void setJsonBody(@NonNull JObject jobj) {
        setBody(ObjectUtils.utf8Bytes(jobj.toString()));
        setContentType("application/json");
    }

    public void setBody(@NonNull byte[] data) {
        this.body = data;
        headers.put("Content-Type", contentType);
    }

    public final void setContentType(@NonNull String contentType) {
        this.contentType = contentType;
        headers.put("Content-Type", contentType);
    }

    @NonNull
    public final byte[] getBody() {
        return body;
    }

    @NonNull
    public final String getContentType() {
        return contentType;
    }
}
