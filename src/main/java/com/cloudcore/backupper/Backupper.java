package com.cloudcore.backupper;

import com.cloudcore.backupper.core.FileSystem;
import com.cloudcore.backupper.server.Command;
import com.cloudcore.backupper.utils.CommandUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public void backupCoins(Command command) {
        FileSystem.createBackupDirectory(command.toPath);

        FileSystem.copyFiles(new File(FileSystem.BankFolder), new File(FileSystem.backupFolder),false);
        FileSystem.copyFiles(new File(FileSystem.FrackedFolder), new File(FileSystem.backupFolder),false);
        FileSystem.copyFiles(new File(FileSystem.GalleryFolder), new File(FileSystem.backupFolder),false);
        FileSystem.copyFiles(new File(FileSystem.ValutFolder), new File(FileSystem.backupFolder),false);
        FileSystem.copyFiles(new File(FileSystem.LostFolder), new File(FileSystem.backupFolder),false);
        FileSystem.copyFiles(new File(FileSystem.MindFolder), new File(FileSystem.backupFolder),false);
        System.out.println("Backup completed");
    }
}