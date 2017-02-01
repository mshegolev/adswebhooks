package com.ringcentral.adswebhooks;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    private static int deplyment_id = 16247, port = 80, port_ads = 80, port_glip= 80;
    private static String protocol_ads = "http", instance_name_ads = "ads-qa.lab.nordigy.ru", path_ads = "/api/v2/deployment/";
    private static String protocol_glip = "https", instance_name_glip = "hooks.glip.com",
                            path_glip = "/webhook/", webhook_id_glip = "94ab4ac8-191a-4a8c-ad35-5664b75fb5bb";
    final String USER_AGENT = "Mozilla/5.0";
    String username = "loaduser1", password = "loaduser1";

    public static void main(String[] args) throws Exception {
        send_web_hook();
        JsonElement jelement = new JsonParser().parse(getJsonDeployment(deplyment_id).toString());
        String state = jelement.getAsJsonObject().get("state").getAsString();
        if (state.equals("Finished") || state.equals("Stopped")) System.out.println("include");;

    }

    private static void send_web_hook() throws IOException {


    }


    static URL url_generator_ads() throws MalformedURLException {
        return url_generator(protocol_ads, instance_name_ads, port_ads, path_ads);
    }
    static URL url_generator_glip() throws MalformedURLException {
        return url_generator(protocol_glip, instance_name_glip, port_glip, path_glip);
    }

    static URL url_generator(String protocol, String dns, int port, String path) throws MalformedURLException {
        return new URL(protocol, dns, port, path);
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
        return url_generator_ads().toString() + deplyment_id;
    }
    private static String create_webhook_url() throws MalformedURLException {
        return url_generator_glip().toString() + webhook_id_glip;
    }

    private void sendPost() throws Exception {
        String url = create_webhook_url();
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
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
