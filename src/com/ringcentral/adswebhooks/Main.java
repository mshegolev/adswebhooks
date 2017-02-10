package com.ringcentral.adswebhooks;

import java.util.Objects;

public class Main {

    public static void main(String[] args) throws Exception {
        LoadConfiguration conf = new LoadConfiguration();

        int[] environmentList = new AdsClient().getEvnironmentId();
        int deployment_id = conf.getDeployment_id(); //get id for environment


        String protocol_glip = conf.getProtocol_glip();
        String instance_name_glip = conf.getInstance_name_glip();
        String path_glip = conf.getPath_glip();
        String webhook_id_glip = conf.getWebhook_id_glip();
        int port_glip = conf.getPort_glip();

        Objects response = (Objects) new AdsClient().getJsonEnvironmentList();
        //JsonElement jelement = new JsonParser().parse(response.toString());
//
//        boolean result;
//        do {
//            Object resultJson = new AdsClient().getJsonDeploymentId(deployment_id);
//            JsonElement jelement = new JsonParser().parse(resultJson.toString());
//            String state = jelement.getAsJsonObject().get("state").getAsString();
//
//            String deployment_url = jelement.getAsJsonObject().get("environment").getAsString() + "deployment/" + deployment_id;
//
//            String body = jelement.toString().replaceAll("\"", " \" ");
//            if (result = state.equals("Finished") || state.equals("Stopped")) {
//
//                String url = new URL(protocol_glip, instance_name_glip, port_glip, path_glip).toString() + webhook_id_glip;
//                URL obj = new URL(url);
//                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
//                con.setRequestMethod("POST");
//                con.setRequestProperty("User-Agent", "Mozilla/5.0");
//                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//                con.setRequestProperty("Content-Type", "application/json");
//
//                String urlParameters = Data.generateBody(state, body, deployment_url);
//
//                con.setDoOutput(true);
//                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//                wr.writeBytes(urlParameters);
//                wr.flush();
//                wr.close();
//
//                int responseCode = con.getResponseCode();
//                System.out.println("\nSending 'POST' request to URL : " + url);
//                System.out.println("Post parameters : " + urlParameters);
//                System.out.println("Response Code : " + responseCode);
//
//                BufferedReader in = new BufferedReader(
//                        new InputStreamReader(con.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//            }
//        } while (!result);

    }

}
