package com.ringcentral.adswebhooks;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Main {


    private static String createCredentionals(String username, String password) {
        String login = username + ":" + password;
        return new String(Base64.encodeBase64(login.getBytes()));
    }

    private static String generateBody(String state, String dataBody, String deployment_url) {
        JsonObject data = new JsonObject();
        data.addProperty("title", "Deployment status: " + state + " URL: "
                + deployment_url);
        data.addProperty("activity", "ads.webhooks");
        data.addProperty("body", dataBody);
        return data.toString();
    }

    private static void setDeplyment_id(int deplyment_id) {
        deplyment_id = deplyment_id;
    }

    public static void main(String[] args) throws Exception {
        LoadConfiguration conf = new LoadConfiguration();
        int deployment_id = conf.getDeployment_id();
        String protocol_ads = conf.getProtocol_ads();
        String protocol_glip = conf.getProtocol_glip();
        String instance_name_glip = conf.getInstance_name_glip();
        String path_glip = conf.getPath_glip();
        String webhook_id_glip = conf.getWebhook_id_glip();
        String instance_name_ads = conf.getInstance_name_ads();
        String path_ads = conf.getPath_ads();
        String status;
        String username_ads = conf.getUsername_ads();
        String password_ads = conf.getPassword_ads();
        int port_ads = conf.getPort_ads(), port_glip = conf.getPort_glip();


        boolean result;
        do {
            String adsUrl = new URL(protocol_ads, instance_name_ads, port_ads, path_ads).toString() + deployment_id;

            Document document = Jsoup
                    .connect(adsUrl)
                    .header("Authorization", "Basic " + createCredentionals(username_ads, password_ads))
                    .get();

            Object resultJson = document.getElementsByTag("textarea").get(0).childNode(0).toString();
            JsonElement jelement = new JsonParser().parse(resultJson.toString());
            String state = jelement.getAsJsonObject().get("state").getAsString();

            String deployment_url = jelement.getAsJsonObject().get("environment").getAsString() + "deployment/" + deployment_id;

            String body = jelement.toString().replaceAll("\"", " \" ");
            if (result = state.equals("Finished") || state.equals("Stopped")) {

                String url = new URL(protocol_glip, instance_name_glip, port_glip, path_glip).toString() + webhook_id_glip;
                URL obj = new URL(url);
                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setRequestProperty("Content-Type", "application/json");

                String urlParameters = generateBody(state, body, deployment_url);

                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
        } while (!result);

    }

}
