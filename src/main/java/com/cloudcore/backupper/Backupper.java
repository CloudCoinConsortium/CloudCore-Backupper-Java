package com.cloudcore.backupper;

import com.cloudcore.backupper.core.FileSystem;
import com.cloudcore.backupper.utils.CommandUtil;
import com.cloudcore.backupper.utils.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.cloudcore.backupper.core.FileSystem.CommandFolder;
import static com.cloudcore.backupper.core.FileSystem.CommandHistory;


public class Backupper {


    /* Methods */
    /**
     * Asks the user for instructions on how to export CloudCoins to new files.
     *
     */
    public static final String RootPath = Paths.get("").toAbsolutePath().toString() + File.separator;

    public Backupper() {
        try {
            createDirectories();
            CommandUtil.saveCommand(CommandUtil.makeCommand());

        } catch (IOException ex) {
            Logger.getLogger(Backupper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void createDirectories() {
        try {
            Files.createDirectories(Paths.get(RootPath));
            Files.createDirectories(Paths.get(CommandFolder));
            Files.createDirectories(Paths.get(CommandHistory));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backupCoins() {

        Scanner reader = new Scanner(System.in);

        // Ask for Back up.
        System.out.println("Do you want to backup your CloudCoin?");
        System.out.println("1 => backup");
        System.out.println("2 => Exit");

        int userChoice = reader.hasNextInt() ? reader.nextInt() : -1;


        if (userChoice < 1 || userChoice > 1) {
            if (userChoice == 2) {
                System.out.println("User have cancel backup process. Exiting...");
            } else {
                System.out.println("invalid Choice. No CloudCoins were backuped. Exiting...");
            }
        } else {
            String commandContent = FileUtils.getCommandFileContnet(CommandFolder);
            String backupAccount = "", backupPath = "";
            try {
                if (commandContent.isEmpty()) {
                    FileSystem.createBackupDirectory(backupPath);
                } else {
                    JSONObject jObj = new JSONObject(commandContent);
                    if (jObj.has("account")) {
                        backupAccount = jObj.getString("account");
                    }
                    if (jObj.has("toPath")) {
                        backupPath = jObj.getString("toPath");
                    }
                    if (backupAccount.isEmpty() || backupAccount.equalsIgnoreCase("default")) {
                        FileSystem.createFoldersWithAccountPassword("");
                        System.out.println("No Backup Account is specified in command file. Going to backup at default location");
                    } else {
                        String password = FileUtils.getAccountFileName(backupAccount);
                        FileSystem.createFoldersWithAccountPassword(password);
                    }
                    if (backupPath.isEmpty() || backupPath.equalsIgnoreCase("default")) {
                        System.out.println("No Backup path is specfied in command file. Going to backup at default location");
                        FileSystem.createBackupDirectory("");
                    } else {
                        System.out.println("Taking backup at " + backupPath);
                        FileSystem.createBackupDirectory(backupPath);
                    }
                }

                FileSystem.copyFiles(new File(FileSystem.BankFolder), new File(FileSystem.backupFolder),false);
                FileSystem.copyFiles(new File(FileSystem.FrackedFolder), new File(FileSystem.backupFolder),false);
                FileSystem.copyFiles(new File(FileSystem.GalleryFolder), new File(FileSystem.backupFolder),false);
                FileSystem.copyFiles(new File(FileSystem.ValutFolder), new File(FileSystem.backupFolder),false);
                FileSystem.copyFiles(new File(FileSystem.LostFolder), new File(FileSystem.backupFolder),false);
                FileSystem.copyFiles(new File(FileSystem.MindFolder), new File(FileSystem.backupFolder),false);
                System.out.println("Backup completed");
            } catch (JSONException ex) {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}