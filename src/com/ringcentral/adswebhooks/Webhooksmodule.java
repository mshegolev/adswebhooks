package com.ringcentral.adswebhooks;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mikhail.shchegolev on 27.01.2017.
 */
public class Webhooksmodule {
    private final String USER_AGENT = "Mozilla/5.0";

    static void test() throws Exception {

//        Webhooksmodule http = new Webhooksmodule();
        //System.out.println("Testing 1 - Send Http GET request");
        //http.sendGet();

    }

    // HTTP POST request
    public static void sendPost(String env_name, int env_id, String status, int deployment_id) throws Exception {

        String url = "https://hooks.glip.com/webhook/94ab4ac8-191a-4a8c-ad35-5664b75fb5bb";
        //String url = "https://hooks.glip.com/webhook/7c656f82-74c5-4d2a-b883-90df7ed10a07";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "USER_AGENT");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");

        String urlParameters = "{\"title\":\"ENV:" + env_name + " DEPLOYMENT PLAN IS " + status + "\",\"activity\":\"ads.webhooks\",\"body\":\"https://ads-qa.lab.nordigy.ru/environment/"+env_id+"/deployment/"+deployment_id+"/\"}";

        // Send post request
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

        //print result
        System.out.println(response.toString());

    }

    // HTTP GET request
    private void sendGet() throws Exception {

        String url = "https://hooks.glip.com/webhook/94ab4ac8-191a-4a8c-ad35-5664b75fb5bb";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type: application/json", "{}");


        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

}

