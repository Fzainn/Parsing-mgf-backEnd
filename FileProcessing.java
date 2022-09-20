package com.example.file;


import java.io.*;
import java.util.ArrayList;

public class FileProcessing {
    public ArrayList<model> FileProcessingFunc(String filePath) {
        File file = new File(
                filePath);
        BufferedReader br
                = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String line;
        boolean beginIons = false;
        boolean permass = false;
        boolean rins_found = false;
        model modelObj = new model();
        ArrayList<model> data = new ArrayList<>();
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (line.contains("BEGIN")){
                beginIons = true;
                continue;
            }
            if (line.contains("PEPMASS") && beginIons){
                permass=true;
                String[] words1 = line.split("=");
                String[] words2 = words1[1].split(" ");
                modelObj.MASS=words2[0];modelObj.INTENSITY=words2[1];
                continue;
            }
            if (line.contains("RTINSECONDS") && beginIons && permass) {
                rins_found=true;

                String[] words1 = line.split("=");
                modelObj.RTINSECONDS=words1[1];
                continue;
            }
            if (line.contains("END") && beginIons && permass && rins_found) {
                //System.out.printf("%s %s \n%s",modelObj.x, modelObj.y,modelObj.rins);
                data.add(modelObj);
                modelObj = new model();
                beginIons=false;
                permass=false;
                rins_found=false;
            }
        }
        return data;
    }
    public static class model {
        public String MASS,INTENSITY,RTINSECONDS;
    }
}