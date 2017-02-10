package com.ringcentral.adswebhooks;

import com.google.gson.JsonObject;

/**
 * Created by mikhail.shchegolev on 10.02.2017.
 */
public class Data {
    static String generateBody(String state, String dataBody, String deployment_url) {
        JsonObject data = new JsonObject();
        data.addProperty("title", "Deployment status: " + state + " URL: "
                + deployment_url);
        data.addProperty("activity", "ads.webhooks");
        data.addProperty("body", dataBody);
        return data.toString();
    }
}
