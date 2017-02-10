package com.ringcentral.adswebhooks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by mikhail.shchegolev on 10.02.2017.
 */
public class LoadConfiguration extends Main {
    public String protocol_ads;
    private String protocol_glip;
    private String instance_name_glip;
    private String path_glip;
    private String webhook_id_glip;
    private String instance_name_ads;
    private String path_ads;
    private String username_ads;
    private String password_ads;
    private int deployment_id, port, port_ads, port_glip;
    private boolean result;

    LoadConfiguration() throws IOException {
        Properties prop = new Properties();
        try {

            prop.load(new FileInputStream("adswebhooks.lab.config"));
            protocol_glip = prop.getProperty("protocol_glip");
            instance_name_glip = prop.getProperty("instance_name_glip");
            path_glip = prop.getProperty("path_glip");
            webhook_id_glip = prop.getProperty("webhook_id_glip");
            protocol_ads = prop.getProperty("protocol_ads");
            instance_name_ads = prop.getProperty("instance_name_ads");
            path_ads = prop.getProperty("path_ads");
            username_ads = prop.getProperty("username_ads");
            password_ads = prop.getProperty("password_ads");
            port_ads = Integer.parseInt(prop.getProperty("port_ads"));
            port_glip = Integer.parseInt(prop.getProperty("port_glip"));
            deployment_id =Integer.parseInt(prop.getProperty("deployment_id"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String getProtocol_glip() {
        return protocol_glip;
    }

    public void setProtocol_glip(String protocol_glip) {
        this.protocol_glip = protocol_glip;
    }

    public String getInstance_name_glip() {
        return instance_name_glip;
    }

    public void setInstance_name_glip(String instance_name_glip) {
        this.instance_name_glip = instance_name_glip;
    }

    public String getPath_glip() {
        return path_glip;
    }

    public void setPath_glip(String path_glip) {
        this.path_glip = path_glip;
    }

    public String getWebhook_id_glip() {
        return webhook_id_glip;
    }

    public void setWebhook_id_glip(String webhook_id_glip) {
        this.webhook_id_glip = webhook_id_glip;
    }

    public String getProtocol_ads() {
        return protocol_ads;
    }

    public void setProtocol_ads(String protocol_ads) {
        this.protocol_ads = protocol_ads;
    }

    public String getInstance_name_ads() {
        return instance_name_ads;
    }

    public void setInstance_name_ads(String instance_name_ads) {
        this.instance_name_ads = instance_name_ads;
    }

    public String getPath_ads() {
        return path_ads;
    }

    public void setPath_ads(String path_ads) {
        this.path_ads = path_ads;
    }

    public String getUsername_ads() {
        return username_ads;
    }

    public void setUsername_ads(String username_ads) {
        this.username_ads = username_ads;
    }

    public String getPassword_ads() {
        return password_ads;
    }

    public void setPassword_ads(String password_ads) {
        this.password_ads = password_ads;
    }

    public int getDeployment_id() {
        return deployment_id;
    }

    public void setDeployment_id(int deployment_id) {
        this.deployment_id = deployment_id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort_ads() {
        return port_ads;
    }

    public void setPort_ads(int port_ads) {
        this.port_ads = port_ads;
    }

    public int getPort_glip() {
        return port_glip;
    }

    public void setPort_glip(int port_glip) {
        this.port_glip = port_glip;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
