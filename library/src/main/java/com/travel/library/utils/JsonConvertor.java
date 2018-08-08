package com.travel.library.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import okhttp3.RequestBody;

/**
 * Created by wisn on 2017/8/22.
 */

public class JsonConvertor {
    private static Gson gson = null;

    private JsonConvertor() {
    }

    private static Gson getInstance() {
        if (gson == null) {
            synchronized (JsonConvertor.class) {
                if (gson == null) {
                    gson = buildGsondisableHtmlEscaping();
                }
            }
        }
        return gson;
    }

    public static RequestBody getRequestBody(Object object) {
        String json = getInstance().toJson(object);
        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
    }

    public static RequestBody getRequestBodybyJson(String json) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
    }


    private static Gson buildGsondisableHtmlEscaping() {
        return new GsonBuilder()
                .disableHtmlEscaping()
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
    }
}