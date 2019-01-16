package com.cloudcore.backupper.core;

import com.cloudcore.backupper.server.Command;
import com.cloudcore.backupper.utils.FileUtils;
import com.cloudcore.backupper.utils.Utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FileSystem {


    /* Fields */
    public static String RootPath = "C:\\Users\\Public\\Documents\\CloudCoin\\Accounts\\DefaultUser\\";

    public static String CommandFolder = RootPath + Config.TAG_CLOUD_COIN + File.separator + Config.TAG_COMMAND + File.separator;

    public static String CommandHistory = RootPath + Config.TAG_CLOUD_COIN + File.separator + Config.TAG_COMMAND + File.separator + "CommandHistory" + File.separator;
    public static String LogsFolder = RootPath + Config.TAG_CLOUD_COIN + File.separator + Config.TAG_LOGS + File.separator
            + Config.TAG_BACKUPER + File.separator;
    public static String AccountFolder = RootPath + Config.TAG_CLOUD_COIN + File.separator + Config.TAG_ACCOUNTS
            + File.separator + Config.TAG_PASSWORDS + File.separator;
    public static String backupFolder = LogsFolder + Config.TAG_BACKUP_DEFAULT + File.separator;

    public static String GalleryFolder = RootPath + Config.TAG_GALLERY + File.separator;
    public static String BankFolder = RootPath + Config.TAG_BANK + File.separator;
    public static String FrackedFolder = RootPath + Config.TAG_FRACKED + File.separator;
    public static String ValutFolder = RootPath + Config.TAG_VALUT + File.separator;
    public static String LostFolder = RootPath + Config.TAG_LOST + File.separator;
    public static String MindFolder = RootPath + Config.TAG_MIND + File.separator;

    public static String Tag_account = RootPath + Config.TAG_CLOUD_COIN + File.separator + Config.TAG_ACCOUNTS;
//    public static String TemplateFolder = RootPath + Config.TAG_TEMPLATES + File.separator;


    /* Methods */
    /**
     * Get the current time for creating backup folder
     *
     * @return current time in specified format
     */
    public static String getBackUpTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MMM dd HH.mm ss a");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /**
     * Creates directories in the location defined by RootPath.
     *
     * @return true if all folders were created or already exist, otherwise
     * false.
     */
    public static boolean createDirectories() {
        try {

            Files.createDirectories(Paths.get(RootPath));
            Files.createDirectories(Paths.get(CommandFolder));
            Files.createDirectories(Paths.get(LogsFolder));
            Files.createDirectories(Paths.get(AccountFolder));
            Files.createDirectories(Paths.get(backupFolder));
//            Files.createDirectories(Paths.get(GalleryFolder));
//            Files.createDirectories(Paths.get(BankFolder));
//            Files.createDirectories(Paths.get(FrackedFolder));
        } catch (IOException e) {
            System.out.println("FS#CD: " + e.getLocalizedMessage());
            return false;
        }

        return true;
    }

    public static void changeRootPath(String rootPath) {
        RootPath = rootPath;

        CommandFolder = RootPath + Config.TAG_CLOUD_COIN + File.separator + Config.TAG_COMMAND + File.separator;

        CommandHistory = RootPath + Config.TAG_CLOUD_COIN + File.separator + Config.TAG_COMMAND + File.separator + "CommandHistory" + File.separator;
        LogsFolder = RootPath + Config.TAG_CLOUD_COIN + File.separator + Config.TAG_LOGS + File.separator + Config.TAG_BACKUPER + File.separator;
        AccountFolder = RootPath + Config.TAG_CLOUD_COIN + File.separator + Config.TAG_ACCOUNTS + File.separator + Config.TAG_PASSWORDS + File.separator;
        backupFolder = LogsFolder + Config.TAG_BACKUP_DEFAULT + File.separator;

        GalleryFolder = RootPath + Config.TAG_GALLERY + File.separator;
        BankFolder = RootPath + Config.TAG_BANK + File.separator;
        FrackedFolder = RootPath + Config.TAG_FRACKED + File.separator;
        ValutFolder = RootPath + Config.TAG_VALUT + File.separator;
        LostFolder = RootPath + Config.TAG_LOST + File.separator;
        MindFolder = RootPath + Config.TAG_MIND + File.separator;

        Tag_account = RootPath + Config.TAG_CLOUD_COIN + File.separator + Config.TAG_ACCOUNTS;
    }

    public static ArrayList<Command> getCommands() {
        String[] commandFiles = FileUtils.selectFileNamesInFolder(CommandFolder);
        ArrayList<Command> commands = new ArrayList<>();

        for (int i = 0, j = commandFiles.length; i < j; i++) {
            if (!commandFiles[i].contains(Config.MODULE_NAME))
                continue;

            try {
                String json = new String(Files.readAllBytes(Paths.get(CommandFolder + commandFiles[i])));
                Command command = Utils.createGson().fromJson(json, Command.class);
                command.filename = commandFiles[i];
                commands.add(command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return commands;
    }

    public static void archiveCommand(Command command) {
        try {
            Files.move(Paths.get(CommandFolder + command.filename),
                    Paths.get(LogsFolder + command.filename),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates directories in the location defined by RootPath.
     *
     * @param path for the folder to backup CloudCoin files.
     *
     * @return true if backup folders were created, otherwise false.
     */
    public static boolean createBackupDirectory(String path) {
        try {
            if (!path.isEmpty()) {
                Path createDirectories = Files.createDirectories(Paths.get(path + File.separator + Config.TAG_BACKUP + getBackUpTime()));
                backupFolder = createDirectories.toString();
            }
        } catch (IOException e) {
            System.out.println("FS#CD: " + e.getLocalizedMessage());
            return false;
        }

        return true;
    }

    /**
     * Creates directories in the location defined by RootPath.
     *
     * @param acccount_pass to create all folders for backup.
     *
     * @return true if backup folders were created, otherwise false.
     */
    public static boolean createFoldersWithAccountPassword(String acccount_pass) {
        try {

            if (!acccount_pass.isEmpty()) {
                Path createDirectories = Files.createDirectories(Paths.get(Tag_account + File.separator + acccount_pass.trim() + File.separator));
                GalleryFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + Config.TAG_GALLERY + File.separator)).toString();
                BankFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + Config.TAG_BANK + File.separator)).toString();
                FrackedFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + Config.TAG_FRACKED + File.separator)).toString();
                ValutFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + Config.TAG_VALUT + File.separator)).toString();
                LostFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + Config.TAG_LOST + File.separator)).toString();
                MindFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + Config.TAG_MIND + File.separator)).toString();


            } else {
                Path createDirectories = Files.createDirectories(Paths.get(Tag_account + File.separator + "DefaultUser" + File.separator));
                GalleryFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + Config.TAG_GALLERY + File.separator)).toString();
                BankFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + Config.TAG_BANK + File.separator)).toString();
                FrackedFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + Config.TAG_FRACKED + File.separator)).toString();
                ValutFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + Config.TAG_VALUT + File.separator)).toString();
                LostFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + Config.TAG_LOST + File.separator)).toString();
                MindFolder = Files.createDirectories(Paths.get(createDirectories.toString() + File.separator + Config.TAG_MIND + File.separator)).toString();

            }
        } catch (IOException e) {
            System.out.println("FS#CD: " + e.getLocalizedMessage());
            return false;
        }

        return true;
    }

    /**
     * Creates directories in the location defined by RootPath.
     *
     * @param sourceDirectory directory which will be backup.
     * @param destinationDirectory in which the backup will be taken
     *
     *
     * @return true if backup is taken successfully.
     */
    public static boolean copyFiles(File sourceDirectory, File destinationDirectory, boolean rename) {
        try {
            if (sourceDirectory.isDirectory()) {
                if (!destinationDirectory.exists()) {
                    destinationDirectory.mkdir();
                }

                String[] children = sourceDirectory.list();
                for (String children1 : children) {
                    if (rename){
                        if (!children1.equals("CommandHistory")) {
                            copyFiles(new File(sourceDirectory, children1), new File(destinationDirectory, "backupper.backup_" + getBackUpTime() + ".txt"), rename);
                        }
                    }else {
                        copyFiles(new File(sourceDirectory, children1), new File(destinationDirectory, children1), rename);
                    }
                }
            } else {

                OutputStream out;
                try (InputStream in = new FileInputStream(sourceDirectory)) {
                    out = new FileOutputStream(destinationDirectory);
                    // Copy the bits from instream to outstream
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                }

                out.close();
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public static void copyFile(String fileName, String sourceFolder, String targetFolder) {
        try {
            Files.copy(Paths.get(sourceFolder + fileName), Paths.get(targetFolder + fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}