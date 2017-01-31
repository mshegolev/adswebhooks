package com.ringcentral.adswebhooks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Base64;

/**
 * Created by mikhail.shchegolev on 27.01.2017.
 */
public class Adsmodule {
    private static String gg;
    private final String USER_AGENT = "Mozilla/5.0";


    public static Statement createConnection(String hostname, int port, String database, String user, String password) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        conn = DriverManager.getConnection(
                "jdbc:postgresql://" + hostname + ":" + port + "/" + database + "", "" + user + "", "" + password + "");
        Statement st = conn.createStatement();
        return st;
    }

    public static Statement createConnection() throws SQLException {
        return createConnection("qa101-ads-ddb01.lab.nordigy.ru", 9999, "rc_ads", "ads_user", "trollolo");
    }


    public static String getEnvName(int env_id) throws SQLException {
        Statement st = createConnection();
        ResultSet rs = st.executeQuery("select name from environments_environment  where id=" + env_id);
        String result = null;
        while (rs.next()) {
            System.out.print("Column 1 returned ");
            System.out.println(result = rs.getString(1));
        }
        rs.close();
        st.close();
        return result;
    }

    public static String getStatus(int env_id, int deployment_id) throws SQLException {
        Statement st = createConnection();
        ResultSet env_name = st.executeQuery("select name from environments_environment  where id=" + env_id);
        String result = null;
        do {
            ResultSet rs = st.executeQuery("SELECT state FROM deploymgr_deployment where id=" + deployment_id + " and environment_id=" + env_id);

            while (rs.next()) {
                System.out.print("Column 1 returned ");
                System.out.println(result = rs.getString(1));
            }
            rs.close();
        } while (!result.equals("Stopped") & !result.equals("Finished"));
        st.close();
        return result;
    }

    // HTTP GET request
    protected static void getEnvironmentState() throws IOException {

    }


    private void sendGet() throws Exception {

        String url = "https://ads-qa.lab.nordigy.ru/api/v2/deployment/16247/";

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
