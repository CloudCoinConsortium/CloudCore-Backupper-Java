package com.cloudcore.backupper.utils;

import com.cloudcore.backupper.core.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class FileUtils {


    /* Methods */
    
    /**
     * Returns an array containing all filenames in a directory.
     *
     * @param folderPath the folder to check for files.
     * @return String Array.
     */
    public static String getCommandFile(String folderPath) {
        File folder = new File(folderPath);
        String commandFile = "";
        if (folder.isDirectory()) {

            File[] filenames = folder.listFiles();

            if (null != filenames) {
                for (File file : filenames) {
                    if (file.isFile()) {
                        if (file.getName().contains(Config.TAG_file_name)) {
                            commandFile = file.getAbsolutePath();
                            commandFile = fetchBackupPath(commandFile);
                            break;
                        }
                    }
                }
            }
        }
        return commandFile;
    }

    public static String fetchBackupPath(String filePath) {

        InputStream is;
        try {
            is = new FileInputStream(filePath);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));

            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();

            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
           
            String fileAsString = sb.toString();
            JSONObject jObj =new JSONObject(fileAsString);
            if(jObj.has("path")){
                return jObj.getString("path");
            }
        } catch (IOException | JSONException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
