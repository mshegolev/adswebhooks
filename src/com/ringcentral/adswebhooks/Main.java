package com.ringcentral.adswebhooks;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean work = true;
        Adsmodule.getEnvironmentState();
        int env_id = getEnvId();
        int deployment_id = getDeployment_id();
        String env_name = Adsmodule.getEnvName(env_id);
        String status = Adsmodule.getStatus(env_id, deployment_id);
        Webhooksmodule.sendPost(env_name,env_id,status,deployment_id);


    }

    public static int getEnvId() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите env_id или exit:");
        int env_id = scanner.nextInt();
        return env_id;
    }

    public static int getDeployment_id() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите deployment_id:");
        int deployment_id = scanner.nextInt();
        return deployment_id;
    }

}
