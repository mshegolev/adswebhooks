package com.ringcentral.adswebhooks;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    private static int deplyment_id = 16247, port = 80;
    private static String protocol = "http", ads_instance_name = "ads-qa.lab.nordigy.ru", path = "/api/v2/deployment/";
    final String USER_AGENT = "Mozilla/5.0";
    String username = "loaduser1", password = "loaduser1";

    public static void main(String[] args) throws Exception {
        JsonElement jelement = new JsonParser().parse(getJsonDeployment(deplyment_id).toString());
        String state = jelement.getAsJsonObject().get("state").getAsString();

        System.out.println(state.toString());
    }


    static URL adsUrlDeployments() throws MalformedURLException {
        return adsUrlDeployments(protocol, ads_instance_name, port, path);
    }

    static URL adsUrlDeployments(String protocol, String ads_instance_name, int port, String path) throws MalformedURLException {
        return new URL(protocol, ads_instance_name, port, path);
    }

    private static String createCredentionals() {
        String username = "loaduser1", password = "loaduser1";
        return createCredentionals(username, password);
    }

    private static String createCredentionals(String username, String password) {
        String login = username + ":" + password;
        return new String(Base64.encodeBase64(login.getBytes()));
    }

    static Object getJsonDeployment(int deployment_id) throws Exception {
        String url = create_deployment_url();
        Document document = Jsoup
                .connect(url)
                .header("Authorization", "Basic " + createCredentionals())
                .get();
        return document.getElementsByTag("textarea").get(0).childNode(0).toString();
    }

    private static String create_deployment_url() throws MalformedURLException {
        return adsUrlDeployments().toString() + deplyment_id;
    }

    private void sendPost() throws Exception {

        String url = "https://hooks.glip.com/webhook/94ab4ac8-191a-4a8c-ad35-5664b75fb5bb";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");

        String urlParameters = "{\"title\":\"My title\",\"activity\":\"mikhail.shchegolev\",\"body\":\"environment 26000\"}";

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
}
