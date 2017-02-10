package com.ringcentral.adswebhooks;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

/**
 * Created by mikhail.shchegolev on 10.02.2017.
 */
public class AdsClient extends Main {
    LoadConfiguration conf = new LoadConfiguration();


    public AdsClient(String protocol_ads, String instance_name_ads, int port_ads, String path_ads) throws IOException {
        String adsUrl = new URL(protocol_ads, instance_name_ads, port_ads, path_ads).toString();
    }

    public AdsClient() throws IOException {

    }

    public static Object getJsonDeploymentId(int deployment_id, String protocol_ads, String instance_name_ads, String path_ads, String username_ads, String password_ads, int port_ads) throws IOException {
        String adsUrl = new URL(protocol_ads, instance_name_ads, port_ads, path_ads).toString() + deployment_id;
        Document document = Jsoup
                .connect(adsUrl)
                .header("Authorization", "Basic " + createCredentionals(username_ads, password_ads))
                .get();
        return document.getElementsByTag("textarea").get(0).childNode(0);
    }

    public Object getJsonEnvironmentList() throws IOException {
        String path_ads="/api/v2/env/";
        return getJsonEnvironmentList(conf.getProtocol_ads(), conf.getInstance_name_ads(), path_ads, conf.getUsername_ads(), conf.getPassword_ads(), conf.getPort_ads());
    }

    public Object getJsonEnvironmentList(String protocol_ads, String instance_name_ads, String path_ads, String username_ads, String password_ads, int port_ads) throws IOException {
        String adsUrl = new URL(protocol_ads, instance_name_ads, port_ads, path_ads).toString();
        Document response = Jsoup
                .connect(adsUrl)
                .ignoreContentType(false)
                .header("Allow","GET, HEAD, OPTIONS")
                .header("Content-Encoding","gzip")
                .header("Content-Type","application/json")
                .header("Authorization", "Basic " + createCredentionals(username_ads, password_ads))
                .header("Accept-Language", "en")
                .header("Accept-Encoding","gzip,deflate,sdch")
                .followRedirects(true)
                .get();
        Elements envList = response.getElementsByClass("prettyprint").get(1).getElementsByTag("a");
        String[] envListArray = new String[envList.size()];
        for (int i = 0; i < envList.size(); i++) {
            envListArray[i] =(response.getElementsByClass("prettyprint").get(1).getElementsByTag("a").get(i).childNode(0).toString());
            System.out.println(envListArray[i]);
        }


        //Elements doc = document.getElementsByClass("response-info").get(0).getElementsByClass("prettyprint").first().getElementsByTag("a");
        //doc.first();
        //return (Object) document.getElementsByTag("textarea").get(0).childNode(0);
        Object gg;
        return gg = null;
    }

    private static String createCredentionals(String username, String password) {
        String login = username + ":" + password;
        return new String(Base64.encodeBase64(login.getBytes()));
    }

    public Object getJsonDeploymentId(int deployment_id) throws IOException {
        return getJsonDeploymentId(deployment_id, conf.getProtocol_ads(), conf.getInstance_name_ads(), conf.getPath_ads(), conf.getUsername_ads(), conf.getPassword_ads(), conf.getPort_ads());
    }

    public int[] getEvnironmentId(){

        int[] envlist = new int[]{1, 2, 3, 4};
        return envlist;
    }
}
