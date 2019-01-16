package com.cloudcore.backupper.utils;

import com.cloudcore.backupper.core.Config;
import com.cloudcore.backupper.core.FileSystem;
import org.json.JSONException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtils {


    /* Methods */

    /**
     * Returns an array containing all filenames in a directory.
     *
     * @param folderPath the folder to check for files.
     * @return String Array.
     */
    public static String getCommandFileContnet(String folderPath) {
        File folder = new File(folderPath);
        String commandFile = "";
        if (folder.isDirectory()) {

            File[] filenames = folder.listFiles();

            if (null != filenames) {
                for (File file : filenames) {
                    if (file.isFile()) {
                        if (file.getName().contains(Config.TAG_file_name)) {
                            commandFile = readCommandFile(file.getAbsolutePath());
                            break;
                        }
                    }
                }
            }
        }
        return commandFile;
    }

    /**
     * Returns an array containing all filenames in a directory.
     *
     * @param accountName the account from passwords folder.
     * @return String Array.
     */
    public static String getAccountFileName(String accountName) {
        File folder = new File(FileSystem.AccountFolder);
        String commandFile = "";
        if (folder.isDirectory()) {

            File[] filenames = folder.listFiles();

            if (null != filenames) {
                for (File file : filenames) {
                    if (file.isFile()) {
                        if (file.getName().contains(accountName)) {
                            commandFile = file.getAbsolutePath();
                            commandFile = fetchAccountContent(commandFile);
                            break;
                        }
                    }
                }
            }
        }
        return commandFile;
    }

    public static String readCommandFile(String filePath) {

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

            return sb.toString();
//            String fileAsString = sb.toString();
//            JSONObject jObj =new JSONObject(fileAsString);
//            if(jObj.has("toPath")){
//                return jObj.getString("toPath");
//            }
        } catch (IOException  ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static String fetchAccountContent(String filePath) {

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

            return sb.toString();
        } catch (IOException  ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     * Returns an array containing all filenames in a directory.
     *
     * @param folderPath the directory to check for files
     * @return String[]
     */
    public static String[] selectFileNamesInFolder(String folderPath) {
        File folder = new File(folderPath);
        Collection<String> files = new ArrayList<>();
        if (folder.isDirectory()) {
            File[] filenames = folder.listFiles();

            if (null != filenames) {
                for (File file : filenames) {
                    if (file.isFile()) {
                        files.add(file.getName());
                    }
                }
            }
        }
        return files.toArray(new String[]{});
    }
}