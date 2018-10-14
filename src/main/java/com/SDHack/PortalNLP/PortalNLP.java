package com.SDHack.PortalNLP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PortalNLP {
    public static String classify(String search) throws IOException {
        String pythonScriptPath = "/Users/hemu/Desktop/Project/Artificial-custom-service/search.py";
        String[] cmd = new String[3];
        cmd[0] = "python";
        cmd[1] = pythonScriptPath;
        cmd[2] = search;

        Runtime rt = Runtime.getRuntime();


        Process pr = rt.exec(cmd);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));


        String line = "";
        String rslt = "";

        while((line = bfr.readLine()) != null){
            System.out.println(line);
            rslt += line;
        }

        return rslt;
    }

}
