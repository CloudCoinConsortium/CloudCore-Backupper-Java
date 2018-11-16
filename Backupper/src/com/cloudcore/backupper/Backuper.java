package com.cloudcore.backupper;

import com.cloudcore.backupper.core.FileSystem;
import static com.cloudcore.backupper.core.FileSystem.CommandFolder;
import com.cloudcore.backupper.utils.FileUtils;
import java.io.File;

import java.util.Scanner;

public class Backuper {


    /* Methods */
    /**
     * Asks the user for instructions on how to export CloudCoins to new files.
     */
    public void backupCoins() {

        Scanner reader = new Scanner(System.in);

        // Ask for Bacck up.
        System.out.println("Do you want to backup your CloudCoin?");
        System.out.println("1 => backup");
        System.out.println("2 => Exit");

        int userChoice = reader.hasNextInt() ? reader.nextInt() : -1;

        if (userChoice < 1 || userChoice > 1) {
            if (userChoice == 2) {
                System.out.println("User have cancel backup proecess. Exiting...");
            } else {
                System.out.println("nvalid Choice. No CloudCoins were backuped. Exiting...");
            }
        } else {
            String backupPath = FileUtils.getCommandFile(CommandFolder);
            if (backupPath.isEmpty()) {
                System.out.println("No Backup path is specfied in command file. Going to backup at default location");
            } else {
                System.out.println("Taking backup at " + backupPath);
            }
            FileSystem.createBackupDirectory(backupPath);

            FileSystem.copyFiles(new File(FileSystem.BankFolder), new File(FileSystem.backupFolder));
            FileSystem.copyFiles(new File(FileSystem.FrackedFolder), new File(FileSystem.backupFolder));
            FileSystem.copyFiles(new File(FileSystem.GalleryFolder), new File(FileSystem.backupFolder));

            System.out.println("Backup completed");
        }
    }
}
