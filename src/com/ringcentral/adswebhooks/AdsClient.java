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
    String[] depList = new String[0];

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

    private static String createCredentionals(String username, String password) {
        String login = username + ":" + password;
        return new String(Base64.encodeBase64(login.getBytes()));
    }

    public Document[] getJsonDeploymentList() throws IOException {
        String path_ads = null;
        return getJsonDeploymentList(conf.getProtocol_ads(), conf.getInstance_name_ads(), path_ads, conf.getUsername_ads(), conf.getPassword_ads(), conf.getPort_ads());
    }

    public Document[] getJsonDeploymentList(String protocol_ads, String instance_name_ads, String path_ads, String username_ads, String password_ads, int port_ads) throws IOException {
        int j = 1;
        for (int i = 0; i < j; i++) {
            path_ads = "/api/v2/deployment/?page=" + j;
            int r = getStatusCode(protocol_ads, instance_name_ads, path_ads, username_ads, password_ads, port_ads);
            if (r == 200) j++;
            else j--;
        }
        int s;
        Document[] doc = new Document[j];
        for (int i = 0; i < doc.length; i++) {
            s = i + 1;
            path_ads = new StringBuilder().append("/api/v2/deployment/?page=").append(s).toString();
            doc[i] = getDeploymentPage(protocol_ads, instance_name_ads, path_ads, username_ads, password_ads, port_ads);
        }
        return doc;
    }

    private Document getDeploymentPage(String protocol_ads, String instance_name_ads, String path_ads, String username_ads, String password_ads, int port_ads) throws IOException {

        String adsUrl = new URL(protocol_ads, instance_name_ads, port_ads, path_ads).toString();
        try {
            Document r = Jsoup
                    .connect(adsUrl)
                    .ignoreContentType(false)
                    .header("Allow", "GET, HEAD, OPTIONS")
                    .header("Content-Encoding", "gzip")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic " + createCredentionals(username_ads, password_ads))
                    .header("Accept-Language", "en")
                    .header("Accept-Encoding", "gzip,deflate,sdch")
                    .followRedirects(true)
                    .get();
            return r;
        } catch (IOException e) {
            System.out.println("io - " + e);
            return null;
        }
    }

    private int getStatusCode(String protocol_ads, String instance_name_ads, String path_ads, String username_ads, String password_ads, int port_ads) throws IOException {
        int r = 0;
        String adsUrl = new URL(protocol_ads, instance_name_ads, port_ads, path_ads).toString();
        try {
            r = Jsoup
                    .connect(adsUrl)
                    .ignoreContentType(false)
                    .header("Allow", "GET, HEAD, OPTIONS")
                    .header("Content-Encoding", "gzip")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic " + createCredentionals(username_ads, password_ads))
                    .header("Accept-Language", "en")
                    .header("Accept-Encoding", "gzip,deflate,sdch")
                    .followRedirects(true)
                    .execute().statusCode();
            return r;
        } catch (IOException e) {
            System.out.println("io - " + e);
            return r = 400;
        }
    }

    public String[] getJsonEnvironmentList() throws IOException {
        String path_ads = "/api/v2/env/";
        return getJsonEnvironmentList(conf.getProtocol_ads(), conf.getInstance_name_ads(), path_ads, conf.getUsername_ads(), conf.getPassword_ads(), conf.getPort_ads());
    }

    public String[] getJsonEnvironmentList(String protocol_ads, String instance_name_ads, String path_ads, String username_ads, String password_ads, int port_ads) throws IOException {
        Document response = getDocument(protocol_ads, instance_name_ads, path_ads, username_ads, password_ads, port_ads);
        Elements envList = response.getElementsByClass("prettyprint").get(1).getElementsByTag("a");
        String[] envListArray = new String[envList.size()];
        for (int i = 0; i < envList.size(); i++) {
            envListArray[i] = (response.getElementsByClass("prettyprint").get(1).getElementsByTag("a").get(i).childNode(0).toString());
            //System.out.println(envListArray[i]);
        }
        return envListArray;
    }

    private Document getDocument(String protocol_ads, String instance_name_ads, String path_ads, String username_ads, String password_ads, int port_ads) throws IOException {
        String adsUrl = new URL(protocol_ads, instance_name_ads, port_ads, path_ads).toString();
        return Jsoup
                .connect(adsUrl)
                .ignoreContentType(false)
                .header("Allow", "GET, HEAD, OPTIONS")
                .header("Content-Encoding", "gzip")
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + createCredentionals(username_ads, password_ads))
                .header("Accept-Language", "en")
                .header("Accept-Encoding", "gzip,deflate,sdch")
                .followRedirects(true)
                .get();
    }

    public Object getJsonDeploymentId(int deployment_id) throws IOException {
        return getJsonDeploymentId(deployment_id, conf.getProtocol_ads(), conf.getInstance_name_ads(), conf.getPath_ads(), conf.getUsername_ads(), conf.getPassword_ads(), conf.getPort_ads());
    }

    public int[] getEvnironmentId() {

        int[] envlist = new int[]{1, 2, 3, 4};
        return envlist;
    }
}
