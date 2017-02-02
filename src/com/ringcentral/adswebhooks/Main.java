package com.ringcentral.adswebhooks;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static String protocol_glip, instance_name_glip, path_glip, webhook_id_glip,protocol_ads, instance_name_ads ,
            path_ads,status,username_ads,password_ads;
    private static int deplyment_id, port, port_ads , port_glip ;

    public static void main(String[] args) throws Exception {
        load_config();

        Scanner sc = new Scanner(System.in);
        System.out.print("Input deployiment: ");
        deplyment_id = sc.nextInt();
        sc.close();
        JsonElement jelement = new JsonParser().parse(getJsonDeployment(deplyment_id).toString());
        String state = jelement.getAsJsonObject().get("state").getAsString();
        String deployment_url = jelement.getAsJsonObject().get("environment").getAsString() + "deployment/" + deplyment_id;
        String body = jelement.toString().replaceAll("\"", " \" ");
        if (state.equals("Finished") || state.equals("Stopped"))
            send_webhook_glip2(generateBody(state, body, deployment_url));
    }


    public static void load_config() throws IOException {
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream("adswebhooks.config"));
            protocol_glip = prop.getProperty("protocol_glip");
            instance_name_glip = prop.getProperty("instance_name_glip");
            path_glip= prop.getProperty("path_glip");
            webhook_id_glip= prop.getProperty("webhook_id_glip");
            protocol_ads= prop.getProperty("protocol_ads");
            instance_name_ads= prop.getProperty("instance_name_ads");
            path_ads= prop.getProperty("path_ads");
            username_ads= prop.getProperty("username_ads");
            password_ads= prop.getProperty("password_ads");
            port_ads = Integer.parseInt(prop.getProperty("port_ads"));
            port_glip = Integer.parseInt(prop.getProperty("port_glip"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void send_webhook_glip2(String body) throws IOException {
        String url = create_webhook_url();
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");

        String urlParameters = body;

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


    public static String generateBody(String state, String dataBody, String deployment_url) {
        JsonObject data = new JsonObject();
        data.addProperty("title", "Deployment status: " + state + " URL: "
                + deployment_url);
        data.addProperty("activity", "ads.webhooks");
        data.addProperty("body", dataBody);
        return data.toString();
    }

    public static void setDeplyment_id(int deplyment_id) {
        Main.deplyment_id = deplyment_id;
    }
}
